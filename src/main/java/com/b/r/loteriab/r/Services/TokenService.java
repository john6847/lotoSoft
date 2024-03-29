package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.GlobalConfiguration;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Model.ViewModel.Token;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Notification.Service.AuditEventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private static final Map<String, Token> activeTokens = new HashMap<>();
    private static AuditEventServiceImpl auditEventService;
    private static GlobalConfigurationService globalConfigurationService;

    @Autowired
    public TokenService(AuditEventServiceImpl auditEventService, GlobalConfigurationService globalConfigurationService) {
        TokenService.auditEventService = auditEventService;
        TokenService.globalConfigurationService = globalConfigurationService;
    }

    public static String createAndStoreToken(Users user, Long enterpriseId) {
        boolean existed = TokenService.activeTokens.values().stream().anyMatch(o -> o.getUser().equals(user.getId()));
        if (existed) {
            return TokenService.activeTokens.entrySet().stream().filter(o -> o.getValue().getUser().equals(user.getId())).findAny().get().getKey();
        }
        GlobalConfiguration globalConfiguration = globalConfigurationService.findGlobalConfiguration(enterpriseId);
        Token token = new Token();
        String generatedToken = UUID.randomUUID().toString();
        token.setEnterpriseId(enterpriseId);
        token.setUser(user.getId());
        token.setName(user.getName());
        token.setToken(generatedToken);
        token.setCanBeDeleted(globalConfiguration.isDeleteUserTokenAfterAmountOfTime());
        if (globalConfiguration.isDeleteUserTokenAfterAmountOfTime()){
            token.setLifetime((globalConfiguration.getUserTokenLifeTime() * 60) * 60 * 1000);
        } else {
            token.setLifetime(120 * 60 * 1000);
        }
        if (contains(generatedToken)) {
            createAndStoreToken(user, enterpriseId);
        }
        activeTokens.put(generatedToken, token);
        sendNotification(enterpriseId);
        return generatedToken;
    }

    public static void sendNotification(Long enterpriseId) {
        SampleResponse sampleResponse = new SampleResponse();
        LastNotification last = new LastNotification();
        last.setChanged(false);
        last.setEnterpriseId(enterpriseId);
        last.setDate(new Date());
        last.setIdType((long) 0);
        last.setType(NotificationType.UserConnected.ordinal());

        sampleResponse.getBody().put("users", getConnectedUsers(enterpriseId));
        sampleResponse.getBody().put("added", true);
        last.setSampleResponse(sampleResponse);
        TokenService.auditEventService.sendMessage(sampleResponse, enterpriseId, last);
    }

    public static void remove(String token, Long enterpriseId) {
        TokenService.activeTokens.entrySet().removeIf(key -> key.getKey().equals(token));
        sendNotification(enterpriseId);
    }

    public static void removeTokens(Long enterpriseId) {
        TokenService.activeTokens.entrySet().removeIf(key -> key.getValue().getEnterpriseId().equals(enterpriseId));
        sendNotification(enterpriseId);
    }

    public static List<Pair<Long, String>> getConnectedUsers(Long enterpriseId) {
        return TokenService.activeTokens.values().stream().filter(o -> o.getEnterpriseId().equals(enterpriseId)).map(e -> Pair.with(e.getUser(), e.getName())).collect(Collectors.toList());
    }

    public static boolean contains(String token) {
        return activeTokens.get(token) != null;
    }

    public static Token retrieve(String token) {
        return activeTokens.get(token);
    }

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        logger.info("Evicting expired tokens");
        List<Token> tokens = new ArrayList<>();
        TokenService.activeTokens.forEach((key, value) -> {
            if (new Date().getTime() - value.getCreatedAt().getTime() > value.getLifetime()) {
                tokens.add(value);
            }
        });
        TokenService.activeTokens.entrySet().removeIf(entry -> (new Date().getTime() - entry.getValue().getCreatedAt().getTime()) > entry.getValue().getLifetime());
        if (tokens.size() > 0) {
            for (Token token : tokens) {
                sendNotification(token.getEnterpriseId());
            }
        }
    }
}

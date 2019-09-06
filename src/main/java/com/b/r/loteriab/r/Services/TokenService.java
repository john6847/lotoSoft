package com.b.r.loteriab.r.Services;

import com.b.r.loteriab.r.Model.ViewModel.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private static final Map<String, Token> activeTokens = new HashMap<>();

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        logger.info("Evicting expired tokens");
        TokenService.activeTokens.entrySet().removeIf(entry -> (new Date().getTime() - entry.getValue().getCreatedAt().getTime()) > entry.getValue().getLifetime());
    }

    public static String createAndStoreToken(Long userId, Long enterpriseId) {
        Token token = new Token();
        String generatedToken = UUID.randomUUID().toString();
        token.setEnterpriseId(enterpriseId);
        token.setUser(userId);
        token.setToken(generatedToken);
        token.setLifetime(120 * 60 * 1000);
        if(contains(generatedToken)){
            createAndStoreToken(userId, enterpriseId);
        }
        activeTokens.put(generatedToken, token);
        return generatedToken;
    }


    public static boolean contains(String token) {
        return activeTokens.get(token) != null;
    }

    public static Token retrieve(String token) {
        return activeTokens.get(token);
    }
}

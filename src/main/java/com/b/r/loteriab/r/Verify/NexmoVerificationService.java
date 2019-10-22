package com.b.r.loteriab.r.Verify;

import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.UserRepository;
import com.b.r.loteriab.r.Verify.Errors.VerificationNotFoundException;
import com.b.r.loteriab.r.Verify.Errors.VerificationRequestFailedException;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.verify.VerifyClient;
import com.nexmo.client.verify.VerifyResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class NexmoVerificationService {
    private static final String APPLICATION_BRAND = "2FA Demo";
    private static final int EXPIRATION_INTERVALS = Calendar.MINUTE;
    private static final int EXPIRATION_INCREMENT = 5;
    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifyClient verifyClient;

    public Verification requestVerification(String phone) throws VerificationRequestFailedException {
        Optional<Verification> matches = verificationRepository.findByPhone(phone);
        if (matches.isPresent()) {
            return matches.get();
        }

        return generateAndSaveNewVerification(phone);
    }

    public boolean verify(String phone, String code) throws VerificationRequestFailedException {
        try {
            Verification verification = retrieveVerification(phone);
            if (verifyClient.check(verification.getRequestId(), code).getStatus().getVerifyStatus() == 0) {
                verificationRepository.deleteByPhone(phone);
                return true;
            }

            return false;
        } catch (VerificationNotFoundException e) {
            requestVerification(phone);
            return false;
        } catch (NexmoClientException e) {
            throw new VerificationRequestFailedException(e);
        }
    }

    private Verification retrieveVerification(String phone) throws VerificationNotFoundException {
        Optional<Verification> matches = verificationRepository.findByPhone(phone);
        if (matches.isPresent()) {
            return matches.get();
        }

        throw new VerificationNotFoundException();
    }

    private Verification generateAndSaveNewVerification(String phone) throws VerificationRequestFailedException {
        try {
            VerifyResponse result = verifyClient.verify(phone, APPLICATION_BRAND);
            if (StringUtils.isBlank(result.getErrorText())) {
                String requestId = result.getRequestId();
                Calendar now = Calendar.getInstance();
                now.add(EXPIRATION_INTERVALS, EXPIRATION_INCREMENT);

                Verification verification = new Verification();
                verification.setPhone(phone);
                verification.setExpirationDate(new Date());
                verification.setRequestId(requestId);
                return verificationRepository.save(verification);
            }
        } catch (NexmoClientException e) {
            throw new VerificationRequestFailedException(e);
        }

        throw new VerificationRequestFailedException();
    }

    public void updateAuthentication(Authentication authentication, Long enterpriseId) {
        Role role = retrieveRoleFromDatabase(authentication.getName(), enterpriseId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) role);

        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

    private Role retrieveRoleFromDatabase(String username, Long enterpriseId) {
        Optional<Users> match = userRepository.findUsersByUsernameAndEnterpriseId(username, enterpriseId);
        if (match.isPresent()) {
            return (Role) match.get().getRoles();
        }

        throw new UsernameNotFoundException("Username not found.");
    }
}
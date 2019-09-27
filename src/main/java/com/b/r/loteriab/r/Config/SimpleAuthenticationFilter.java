package com.b.r.loteriab.r.Config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_ENTERPRISE_KEY = "enterprise";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!request.getMethod()
                .equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager()
                .authenticate(authRequest);
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String enterprise = obtainEnterprise(request);

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        if (enterprise == null) {
            enterprise = "";
        }

        String usernameDomain = String.format("%s%s%s", username.trim(),
                String.valueOf(Character.LINE_SEPARATOR), enterprise);
        return new UsernamePasswordAuthenticationToken(usernameDomain, password);
    }

    private String obtainEnterprise(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_ENTERPRISE_KEY);
    }

}
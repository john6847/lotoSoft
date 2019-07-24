package com.b.r.loteriab.r.Config;

/**
 * Created by Dany on 24/04/2019.
 */
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException ex) throws IOException, ServletException {
        response.getOutputStream().print("Access denied");
        response.setStatus(403);
        response.sendRedirect("/error/access-denied");
    }

}

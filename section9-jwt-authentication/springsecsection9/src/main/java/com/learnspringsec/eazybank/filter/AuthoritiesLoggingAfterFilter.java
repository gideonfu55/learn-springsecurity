package com.learnspringsec.eazybank.filter;

import jakarta.servlet.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthoritiesLoggingAfterFilter implements Filter {

    private final Logger log = Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // This is a custom filter that serves the purpose of logging the user's authorities after the authentication process is completed:
        if (authentication != null) {
            log.info("User " + authentication.getName() + " is successfully authenticated and " + "has the authorities: " + authentication.getAuthorities().toString());
        }

        filterChain.doFilter(request, response);

    }
}

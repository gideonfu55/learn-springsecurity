package com.learnspringsec.eazybank.filter;

import com.learnspringsec.eazybank.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieving the current authentication from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if there is an authenticated user
        if (authentication != null) {
            // Create a SecretKey using the JWT_KEY defined in SecurityConstants
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            // Build the JWT token
            String jwt = Jwts.builder()
                .setIssuer("Eazy Bank") // Set the issuer of the token
                .setSubject("JWT Token") // Set the subject of the token
                .claim("username", authentication.getName())  // Set a custom claim for the username
                .claim("authorities", populateAuthorities(authentication.getAuthorities())) // Set authorities as a claim
                .setIssuedAt(new Date()) // Set the token issuance time
                .setIssuedAt(new Date()) // Set the token expiration time
                .setExpiration(new Date((new Date()).getTime() + 30000000))
                .signWith(key) // Sign the token with the SecretKey
                .compact();

            // Set the JWT token in the response header
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Define when this filter should not be applied
        return !request.getServletPath().equals("/user");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        // Convert authorities to a comma-separated string
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}

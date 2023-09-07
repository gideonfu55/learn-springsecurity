package com.learnspringsec.eazybank.config;

import com.learnspringsec.eazybank.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the JWT token from the request header
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        // Check if a JWT token is present
        if (jwt != null) {
            try {
                // Create a SecretKey using the JWT_KEY defined in SecurityConstants
                SecretKey key = Keys.hmacShaKeyFor(
                    SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                // Parse the JWT token and extract its claims
                Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
                // Extract the username and authorities from the claims
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");

                // Create an Authentication object with the extracted information
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                // Set the Authentication object in the SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // Handle exceptions for invalid tokens
                throw new BadCredentialsException("Invalid Token received!");
            }

        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Define when this filter should not be applied (e.g., for specific servlet paths)
        return request.getServletPath().equals("/user");
    }
}

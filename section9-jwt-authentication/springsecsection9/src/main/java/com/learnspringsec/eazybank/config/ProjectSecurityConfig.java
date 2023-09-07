package com.learnspringsec.eazybank.config;

import java.util.Arrays;
import java.util.Collections;

import com.learnspringsec.eazybank.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {

  // Basic structure for customizing your Security Configuration:
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    /**
     * An implementation of the {@link CsrfTokenRequestHandler} interface that is capable of making the {@link CsrfToken} 
     * available as a request attribute and resolving the token value as either a header or parameter value of the request.
     */
    CsrfTokenRequestAttributeHandler csrfTokenHandler = new CsrfTokenRequestAttributeHandler();
    csrfTokenHandler.setCsrfRequestAttributeName("_csrf");

    http
      .sessionManagement(sessionManagement -> sessionManagement
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      // Addition of CORs configuration:
      .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
        @Override
        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
          config.setAllowedMethods(Collections.singletonList("*"));
          config.setAllowCredentials(true);
          config.setAllowedHeaders(Collections.singletonList("*"));
          config.setExposedHeaders(Arrays.asList("Authorization"));
          config.setMaxAge(3600L);
          return config;
        }
      }))

      /**
       * The CookieCsrfTokenRepository class is necessary to persist the CSRF token in a cookie names "XSRF-TOKEN" and reads 
       * from a header named "X-XSRF-TOKEN" following the conventions of Angular JS. When using Angular JS, you need to have the 
       * withHttpOnlyFalse() so that it can be read by the Angular FE.
      */
      .csrf(csrf -> csrf.csrfTokenRequestHandler(csrfTokenHandler).ignoringRequestMatchers("/contact", "/register")
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      )
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
        .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
      .authorizeHttpRequests(requests -> requests
        // Configuration for roles based on the request path (note that these are just examples - the user and admin should both be able to access all bank details in an actual banking application):
        .requestMatchers("/myAccount").hasRole("USER")
        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
        .requestMatchers("/myLoans").hasRole("USER")
        .requestMatchers("/myCards").hasRole("USER")
        // .requestMatchers("/myCards").hasRole("MANAGER") // this won't be retrieved as there are no users with 'MANAGER' role currently

        // Configuration of authentication for all users:
        .requestMatchers("/user").authenticated()
        
        // Configuration of paths (public) for which no authentication is required:
        .requestMatchers("notices", "/contact", "/register").permitAll()
      )
      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  /**
   * This is the recommended way (encoder) to encode passwords in Spring Security 5 and above - using BCryptPasswordEncoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}

package com.learnspringsec.eazybank.config;

import java.util.Collections;

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

import com.learnspringsec.eazybank.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {

  // Basic structure for customizing your Security Configuration:
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

    /**
     * An implementation of the {@link CsrfTokenRequestHandler} interface that is capable of making the {@link CsrfToken} 
     * available as a request attribute and resolving the token value as either a header or parameter value of the request.
     *
     * @author Steve Riesenberg
     * @since 5.8
     */
    CsrfTokenRequestAttributeHandler csrfTokenHandler = new CsrfTokenRequestAttributeHandler();
    csrfTokenHandler.setCsrfRequestAttributeName("_csrf");

    http
      // By default the security context is saved automatically. This can reduce unnecessary database writes caused by frequent updates to the security context:
      .securityContext(context -> context.requireExplicitSave(false))
      // This will mean a session will be created for every request. This is not recommended for production applications:
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
      // Addition of CORs configuration:
      .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
        @Override
        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
          config.setAllowedMethods(Collections.singletonList("*"));
          config.setAllowCredentials(true);
          config.setAllowedHeaders(Collections.singletonList("*"));
          config.setMaxAge(3600L);
          return config;
        }
      }))
      // Rather than simply disabling the CSRF protection, you can customize it to your needs:
      // .csrf(csrf -> csrf.disable())
      // .csrf(csrf -> csrf.ignoringRequestMatchers("/contact", "/register"))

      /**
       * The CookieCsrfTokenRepository class is necessary to persist the CSRF token in a cookie names "XSRF-TOKEN" and reads 
       * from a header named "X-XSRF-TOKEN" following the conventions of Angular JS. When using Angular JS, you need to have the 
       * withHttpOnlyFalse() so that it can be read by the Angular FE.
      */
      .csrf(csrf -> csrf.csrfTokenRequestHandler(csrfTokenHandler).ignoringRequestMatchers("/register")
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      )
      .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
      .authorizeHttpRequests(requests -> requests
        .requestMatchers("/myAccount", "myBalance", "/myLoans", "/myCards", "/contact", "/user").authenticated()
        .requestMatchers("notices", "/register").permitAll()
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

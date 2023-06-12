package com.learnspringsec.eazybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

  // Basic structure for customizing your Security Configuration:
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((requests) -> requests
      .requestMatchers("/myAccount", "myBalance", "/myLoans", "/myCards").authenticated()
      .requestMatchers("notices", "/contact").permitAll())
      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    // Approach to use DefaultPasswordEncoder() method

    UserDetails admin = User.withDefaultPasswordEncoder()
      .username("admin")
      .password("12345")
      .roles("ADMIN")
      .build();

    return new InMemoryUserDetailsManager(admin);
  }

}

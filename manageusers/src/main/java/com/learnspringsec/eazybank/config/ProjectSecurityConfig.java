package com.learnspringsec.eazybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    // Approach 1 to use DefaultPasswordEncoder() method when creating the user details:

    // UserDetails admin = User.withDefaultPasswordEncoder()
    //   .username("admin")
    //   .password("12345")
    //   .authorities("ADMIN")
    //   .build();

    // UserDetails user = User.withDefaultPasswordEncoder()
    //   .username("user")
    //   .password("12345")
    //   .authorities("USER")
    //   .build();

    // return new InMemoryUserDetailsManager(admin, user);

    // Approach 2 where we use NoOpPasswordEncoder Bean when creating the user details:
    UserDetails admin = User.withUsername("admin")
      .password("12345")
      .authorities("ADMIN")
      .build();

    UserDetails user = User.withUsername("user")
      .password("12345")
      .authorities("USER")
      .build();

    return new InMemoryUserDetailsManager(admin, user);

  }

  /**
   * NoOpPasswordEncoder is not recommended for production use.
   * Use only for non-prod environments.
   * 
   * Instead use an adaptive one way function like BCryptPasswordEncoder, Pbkdf2PasswordEncoder, or SCryptPasswordEncoder.
   * 
   * @return PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}

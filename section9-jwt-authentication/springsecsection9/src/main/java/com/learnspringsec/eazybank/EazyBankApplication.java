package com.learnspringsec.eazybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// Add EnableWebSecurity annotation for debugging
@SpringBootApplication
@EnableWebSecurity(debug = true)
public class EazyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankApplication.class, args);
	}

}

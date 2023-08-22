package com.learnspringsec.eazybank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringsec.eazybank.model.Customer;
import com.learnspringsec.eazybank.repository.CustomerRepository;

@RestController
public class LoginController {
  
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody Customer customer) {

    Customer savedCustomer = null;
    ResponseEntity<String> response = null;
    
    try {
      // This is to encode the password before setting the password for customer:
      String hashPwd = passwordEncoder.encode(customer.getPwd());

      // Set password to the encoded password before saving it to the database:
      customer.setPwd(hashPwd);

      savedCustomer = customerRepository.save(customer);
      
      if (savedCustomer.getId() > 0) {
        response = ResponseEntity
          .status(HttpStatus.CREATED)
          .body("Given user details have been registered for user successfully.");
      }
    } catch (Exception e) {
      response = ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An exception occurred due to " + e.getMessage());
    }
    
    return response;

  }

  @RequestMapping("/user")
  public Customer getUserDetailsAfterLogin(Authentication authentication) {
    List<Customer> customers = customerRepository.findByEmail(authentication.getName());
    if (customers != null && customers.size() > 0) {
      return customers.get(0);
    } else {
      return null;
    }
  }

}

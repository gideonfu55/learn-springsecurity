package com.learnspringsec.eazybank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringsec.eazybank.model.Customer;
import com.learnspringsec.eazybank.repository.CustomerRepository;

@RestController
public class LoginController {
  
  @Autowired
  CustomerRepository customerRepository;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody Customer customer) {

    Customer savedCustomer = null;
    ResponseEntity<String> response = null;
    
    try {
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

}

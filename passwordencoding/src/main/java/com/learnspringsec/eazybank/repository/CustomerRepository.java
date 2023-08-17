package com.learnspringsec.eazybank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.learnspringsec.eazybank.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
  
  List<Customer> findByEmail(String email);

}

package com.learnspringsec.eazybank.repository;

import org.springframework.data.repository.CrudRepository;

import com.learnspringsec.eazybank.model.Customer;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByEmail(String email);
    
}

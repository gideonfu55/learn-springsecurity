package com.learnspringsec.eazybank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learnspringsec.eazybank.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
  
}

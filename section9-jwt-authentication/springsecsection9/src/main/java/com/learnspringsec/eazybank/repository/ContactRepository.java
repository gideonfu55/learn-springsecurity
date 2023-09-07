package com.learnspringsec.eazybank.repository;

import org.springframework.data.repository.CrudRepository;

import com.learnspringsec.eazybank.model.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}

package com.learnspringsec.eazybank.repository;

import org.springframework.data.repository.CrudRepository;

import com.learnspringsec.eazybank.model.Accounts;

public interface AccountsRepository extends CrudRepository<Accounts, Long> {
	
	Accounts findByCustomerId(int customerId);

}

package com.learnspringsec.eazybank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.learnspringsec.eazybank.model.Cards;

public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(int customerId);

}

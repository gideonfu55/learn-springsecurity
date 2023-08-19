package com.learnspringsec.eazybank.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.learnspringsec.eazybank.model.AccountTransactions;

public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, Long> {
	
	List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);

}

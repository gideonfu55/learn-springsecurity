package com.learnspringsec.eazybank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringsec.eazybank.model.AccountTransactions;
import com.learnspringsec.eazybank.repository.AccountTransactionsRepository;

@RestController
public class BalanceController {

  @Autowired
  private AccountTransactionsRepository accountTransactionsRepository;

  @GetMapping("/myBalance")
  public List<AccountTransactions> getAccountDetails(@RequestParam int id) {
    List<AccountTransactions> accountTransactions = accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id);

    if (accountTransactions != null) {
      return accountTransactions;
    } else {
      return null;
    }
  }
  
}

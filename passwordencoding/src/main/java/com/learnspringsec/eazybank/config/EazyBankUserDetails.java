package com.learnspringsec.eazybank.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learnspringsec.eazybank.model.Customer;
import com.learnspringsec.eazybank.repository.CustomerRepository;

@Service
public class EazyBankUserDetails implements UserDetailsService {

  @Autowired
  CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String userName, password = null;
    List<GrantedAuthority> authorities = null;
    
    // This is to use the email as username instead:
    List<Customer> customer = customerRepository.findByEmail(username);
    if (customer.size() == 0) {
        throw new UsernameNotFoundException("User details not found for the user : " + username);
    } else {
        userName = customer.get(0).getEmail();
        password = customer.get(0).getPassword();
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
    }
    return new User(userName, password, authorities);
  }
  
}

package com.learnfullstack.springsecuritybasic.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.learnfullstack.springsecuritybasic") // if you are creating this component in different package from where the SpringBootApplication is present (e.g. a sub-package), you need to add this annotation to scan the component
public class WelcomeController {

  @GetMapping("/welcome")
  public String welcome() {
    return "Welcome to Spring Application with Security";
  }
  
}

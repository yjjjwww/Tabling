package com.yjjjwww.tabling.customer.controller;

import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;
import com.yjjjwww.tabling.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody CustomerSignUpForm customerSignUpForm) {
        return ResponseEntity.ok(customerService.signUp(customerSignUpForm));
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody CustomerSignInForm customerSignInForm) {
        return ResponseEntity.ok(customerService.signIn(customerSignInForm));
    }
}

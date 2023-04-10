package com.yjjjwww.tabling.customer.controller;

import com.yjjjwww.tabling.customer.model.CustomerInput;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody CustomerInput customerInput) {
        return ResponseEntity.ok(customerService.signUp(customerInput));
    }
}

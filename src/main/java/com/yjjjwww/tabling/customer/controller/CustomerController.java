package com.yjjjwww.tabling.customer.controller;

import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;
import com.yjjjwww.tabling.customer.model.ReserveRestaurantForm;
import com.yjjjwww.tabling.customer.model.RestaurantDto;
import com.yjjjwww.tabling.customer.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    public static final String TOKEN_HEADER = "Authorization";

    private final CustomerService customerService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody CustomerSignUpForm customerSignUpForm) {
        return ResponseEntity.ok(customerService.signUp(customerSignUpForm));
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody CustomerSignInForm customerSignInForm) {
        return ResponseEntity.ok(customerService.signIn(customerSignInForm));
    }

    @GetMapping("/restaurant")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<RestaurantDto> getRestaurantList() {
        return customerService.getRestaurantList();
    }

    @PostMapping("/restaurant/reservation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> reserveRestaurant(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody ReserveRestaurantForm form
    ) {
        return ResponseEntity.ok(customerService.reserveRestaurant(token, form));
    }
}

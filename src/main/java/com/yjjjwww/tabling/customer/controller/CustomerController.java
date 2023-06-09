package com.yjjjwww.tabling.customer.controller;

import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;
import com.yjjjwww.tabling.customer.model.EditReviewForm;
import com.yjjjwww.tabling.customer.model.RegisterReviewForm;
import com.yjjjwww.tabling.customer.model.ReserveRestaurantForm;
import com.yjjjwww.tabling.customer.model.RestaurantDto;
import com.yjjjwww.tabling.customer.model.VisitRestaurantForm;
import com.yjjjwww.tabling.customer.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @ApiOperation(value = "Customer 회원 가입")
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody CustomerSignUpForm customerSignUpForm) {
        return ResponseEntity.ok(customerService.signUp(customerSignUpForm));
    }

    @ApiOperation(value = "Customer 로그인")
    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody CustomerSignInForm customerSignInForm) {
        return ResponseEntity.ok(customerService.signIn(customerSignInForm));
    }

    @ApiOperation(value = "매장 목록 조회")
    @GetMapping("/restaurant")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<RestaurantDto> getRestaurantList() {
        return customerService.getRestaurantList();
    }

    @ApiOperation(value = "Customer 매장 예약하기")
    @PostMapping("/restaurant/reservation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> reserveRestaurant(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody ReserveRestaurantForm form
    ) {
        return ResponseEntity.ok(customerService.reserveRestaurant(token, form));
    }

    @ApiOperation(value = "Customer 매장 방문 확인")
    @PutMapping("/restaurant/visit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> visitRestaurant(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody VisitRestaurantForm form
    ) {
        return ResponseEntity.ok(customerService.visitRestaurant(token, form));
    }

    @ApiOperation(value = "Customer 리뷰 등록")
    @PostMapping("/restaurant/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> registerReview(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody RegisterReviewForm form
    ) {
        return ResponseEntity.ok(customerService.registerReview(token, form));
    }

    @ApiOperation(value = "Customer 리뷰 수정")
    @PutMapping("/restaurant/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> editReview(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody EditReviewForm form
    ) {
        return ResponseEntity.ok(customerService.editReview(token, form));
    }

    @ApiOperation(value = "Customer 리뷰 삭제")
    @DeleteMapping("/restaurant/review/{reviewId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> deleteReview(
        @PathVariable Long reviewId,
        @RequestHeader(name = TOKEN_HEADER) String token
    ) {
        return ResponseEntity.ok(customerService.deleteReview(token, reviewId));
    }
}

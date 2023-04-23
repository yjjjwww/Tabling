package com.yjjjwww.tabling.manager.controller;

import com.yjjjwww.tabling.manager.model.ManagerSignInForm;
import com.yjjjwww.tabling.manager.model.ManagerSignUpForm;
import com.yjjjwww.tabling.manager.model.RestaurantRegisterForm;
import com.yjjjwww.tabling.manager.model.RestaurantReservationDto;
import com.yjjjwww.tabling.manager.service.ManagerService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/manager")
public class ManagerController {

    public static final String TOKEN_HEADER = "Authorization";

    private final ManagerService managerService;

    @ApiOperation(value = "Manager 회원 가입")
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody ManagerSignUpForm managerSignUpForm) {
        return ResponseEntity.ok(managerService.signUp(managerSignUpForm));
    }

    @ApiOperation(value = "Manager 로그인")
    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody ManagerSignInForm managerSignInForm) {
        return ResponseEntity.ok(managerService.signIn(managerSignInForm));
    }

    @ApiOperation(value = "Manager 파트너 등록")
    @PostMapping("/partner")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> getPartner(@RequestHeader(name = TOKEN_HEADER) String token) {
        return ResponseEntity.ok(managerService.getPartner(token));
    }

    @ApiOperation(value = "Manager 매장 등록")
    @PostMapping("/register/restaurant")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> registerRestaurant(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody RestaurantRegisterForm form
    ) {
        return ResponseEntity.ok(managerService.registerRestaurant(token, form));
    }

    @ApiOperation(value = "Manager 매장 예약 리스트 조회")
    @GetMapping("/restaurant/reservation")
    @PreAuthorize("hasRole('MANAGER')")
    public List<RestaurantReservationDto> getReservations(@RequestHeader(name = TOKEN_HEADER) String token) {
        return managerService.getReservations(token);
    }

    @ApiOperation(value = "매장 예약 승인")
    @PutMapping("/restaurant/reservation/{reservationId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> acceptReservation(
        @PathVariable String reservationId,
        @RequestHeader(name = TOKEN_HEADER) String token
    ) {
        return ResponseEntity.ok(managerService.acceptReservation(token, reservationId));
    }

    @ApiOperation(value = "매장 예약 취소")
    @PutMapping("/restaurant/reservation/cancel/{reservationId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> cancelReservation(
        @PathVariable String reservationId,
        @RequestHeader(name = TOKEN_HEADER) String token
    ) {
        return ResponseEntity.ok(managerService.cancelReservation(token, reservationId));
    }
}

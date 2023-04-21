package com.yjjjwww.tabling.manager.service;

import com.yjjjwww.tabling.manager.model.ManagerSignInForm;
import com.yjjjwww.tabling.manager.model.ManagerSignUpForm;
import com.yjjjwww.tabling.manager.model.RestaurantRegisterForm;
import com.yjjjwww.tabling.manager.model.RestaurantReservationDto;
import java.util.List;

public interface ManagerService {

    /**
     * 회원가입
     */
    String signUp(ManagerSignUpForm managerSignUpForm);

    /**
     * 로그인
     */
    String signIn(ManagerSignInForm managerSignInForm);

    /**
     * 파트너 가입
     */
    String getPartner(String token);

    /**
     * 매장 등록
     */
    String registerRestaurant(String token, RestaurantRegisterForm form);

    /**
     * 대기 중인 예약 신청 목록 보기
     */
    List<RestaurantReservationDto> getReservations(String token);

    /**
     * 예약 승인하기
     */
    String acceptReservation(String token, String reservationId);

    /**
     * 예약 취소하기
     */
    String cancelReservation(String token, String reservationId);
}

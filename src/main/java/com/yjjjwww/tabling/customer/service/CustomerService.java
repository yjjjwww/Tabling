package com.yjjjwww.tabling.customer.service;

import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;
import com.yjjjwww.tabling.customer.model.ReserveRestaurantForm;
import com.yjjjwww.tabling.customer.model.RestaurantDto;
import com.yjjjwww.tabling.customer.model.VisitRestaurantForm;
import java.util.List;

public interface CustomerService {

    /**
     * 회원가입
     */
    String signUp(CustomerSignUpForm customerSignUpForm);

    /**
     * 로그인
     */
    String signIn(CustomerSignInForm customerSignInForm);

    /**
     * 매장 리스트 가져오기
     */
    List<RestaurantDto> getRestaurantList();

    /**
     * 매장 예약하기
     * 매장 예약 코드 리턴
     */
    String reserveRestaurant(String token, ReserveRestaurantForm form);

    /**
     * 매장 방문하기
     */
    String visitRestaurant(String token, VisitRestaurantForm form);
}

package com.yjjjwww.tabling.customer.service;

import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;
import com.yjjjwww.tabling.customer.model.RestaurantDto;
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
}

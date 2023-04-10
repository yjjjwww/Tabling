package com.yjjjwww.tabling.customer.service;

import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;

public interface CustomerService {

    /**
     * 회원가입
     */
    String signUp(CustomerSignUpForm customerSignUpForm);

    /**
     * 로그인
     */
    String signIn(CustomerSignInForm customerSignInForm);
}

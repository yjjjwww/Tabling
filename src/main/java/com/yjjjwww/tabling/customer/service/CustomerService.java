package com.yjjjwww.tabling.customer.service;

import com.yjjjwww.tabling.customer.model.CustomerInput;

public interface CustomerService {

    /**
     * 회원가입
     */
    String signUp(CustomerInput customerInput);
}

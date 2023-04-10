package com.yjjjwww.tabling.manager.service;

import com.yjjjwww.tabling.manager.model.ManagerPartnerForm;
import com.yjjjwww.tabling.manager.model.ManagerSignInForm;
import com.yjjjwww.tabling.manager.model.ManagerSignUpForm;

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
    String getPartner(ManagerPartnerForm managerPartnerForm);
}

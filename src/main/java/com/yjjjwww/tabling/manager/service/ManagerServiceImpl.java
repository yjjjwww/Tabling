package com.yjjjwww.tabling.manager.service;

import com.yjjjwww.tabling.exception.CustomException;
import com.yjjjwww.tabling.exception.ErrorCode;
import com.yjjjwww.tabling.manager.entity.Manager;
import com.yjjjwww.tabling.manager.model.ManagerSignInForm;
import com.yjjjwww.tabling.manager.model.ManagerSignUpForm;
import com.yjjjwww.tabling.manager.repository.ManagerRepository;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    private static final String SIGNUP_SUCCESS = "회원가입 성공";
    private static final String SIGNIN_SUCCESS = "로그인 성공";

    @Override
    public String signUp(ManagerSignUpForm managerSignUpForm) {
        Optional<Manager> optionalManager =
            managerRepository.findByUserId(managerSignUpForm.getUserId());
        if (optionalManager.isPresent()) {
            throw (new CustomException(ErrorCode.ALREADY_SIGNUP_ID));
        }

        if (!isValidPassword(managerSignUpForm.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (!isValidPhone(managerSignUpForm.getPhone())) {
            throw new CustomException(ErrorCode.INVALID_PHONE);
        }

        String encPassword = BCrypt.hashpw(managerSignUpForm.getPassword(), BCrypt.gensalt());

        Manager manager = Manager.builder()
            .userId(managerSignUpForm.getUserId())
            .password(encPassword)
            .phone(managerSignUpForm.getPhone())
            .partnerYn(false)
            .build();

        managerRepository.save(manager);

        return SIGNUP_SUCCESS;
    }

    @Override
    public String signIn(ManagerSignInForm managerSignInForm) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<Manager> optionalCustomer =
            managerRepository.findByUserId(managerSignInForm.getUserId());

        if (optionalCustomer.isEmpty() || !encoder.matches(managerSignInForm.getPassword(),
            optionalCustomer.get().getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_CHECK_FAIL);
        }

        return SIGNIN_SUCCESS;
    }

    /**
     * 핸드폰 번호 형식 검사
     */
    private static boolean isValidPhone(String phone) {
        if (phone.length() > 11 || phone.length() < 5) {
            return false;
        }
        boolean err = false;
        String regex = "^[0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    /**
     * 비밀번호 형식 검사(최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함)
     */
    private static boolean isValidPassword(String password) {
        boolean err = false;
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (m.matches()) {
            err = true;
        }
        return err;
    }
}

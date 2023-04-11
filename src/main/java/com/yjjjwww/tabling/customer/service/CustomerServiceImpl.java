package com.yjjjwww.tabling.customer.service;

import com.yjjjwww.tabling.common.UserType;
import com.yjjjwww.tabling.config.JwtTokenProvider;
import com.yjjjwww.tabling.customer.entity.Customer;
import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;
import com.yjjjwww.tabling.customer.repository.CustomerRepository;
import com.yjjjwww.tabling.exception.CustomException;
import com.yjjjwww.tabling.exception.ErrorCode;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtTokenProvider provider;

    private static final String SIGNUP_SUCCESS = "회원가입 성공";

    @Override
    public String signUp(CustomerSignUpForm customerSignUpForm) {
        Optional<Customer> optionalCustomer =
            customerRepository.findByUserId(customerSignUpForm.getUserId());
        if (optionalCustomer.isPresent()) {
            throw (new CustomException(ErrorCode.ALREADY_SIGNUP_ID));
        }

        if (!isValidPassword(customerSignUpForm.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (!isValidPhone(customerSignUpForm.getPhone())) {
            throw new CustomException(ErrorCode.INVALID_PHONE);
        }

        String encPassword = BCrypt.hashpw(customerSignUpForm.getPassword(), BCrypt.gensalt());

        Customer customer = Customer.builder()
            .userId(customerSignUpForm.getUserId())
            .password(encPassword)
            .phone(customerSignUpForm.getPhone())
            .build();

        customerRepository.save(customer);

        return SIGNUP_SUCCESS;
    }

    @Override
    public String signIn(CustomerSignInForm customerSignInForm) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<Customer> optionalCustomer =
            customerRepository.findByUserId(customerSignInForm.getUserId());

        if (optionalCustomer.isEmpty() || !encoder.matches(customerSignInForm.getPassword(),
            optionalCustomer.get().getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_CHECK_FAIL);
        }

        Customer customer = optionalCustomer.get();

        return provider.createToken(customer.getUserId(), customer.getId(), UserType.CUSTOMER);
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

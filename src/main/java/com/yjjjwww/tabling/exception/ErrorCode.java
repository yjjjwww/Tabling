package com.yjjjwww.tabling.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함해야 합니다."),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, "핸드폰 번호 형식을 확인해주세요."),
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해 주세요."),
    MANAGER_NOT_FOUND(HttpStatus.BAD_REQUEST, "아이디, 패스워드, 핸드폰 번호를 확인해 주세요."),
    ALREADY_PARTNER(HttpStatus.BAD_REQUEST, "이미 파트너 가입되어 있습니다."),
    NOT_PARTNER_MANAGER(HttpStatus.BAD_REQUEST, "파트너 가입 해주세요."),
    ALREADY_SIGNUP_ID(HttpStatus.BAD_REQUEST, "중복된 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

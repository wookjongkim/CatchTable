package com.project.catchtable.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PARTNER_ALREADY_EXIST(400 ,"e0001", "해당 이메일에 대한 파트너가 이미 존재합니다."),
    USER_ALREADY_EXIST(400, "e0002", "해당 이메일에 대한 고객이 이미 존재합니다."),
    PARTNER_ID_INVALID(400, "e0003", "파트터님의 아이디가 틀렸습니다"),
    PARTNER_PASSWORD_INVALID(400, "e0004", "파트터님의 비밀번호가 틀렸습니다"),
    CUSTOMER_ID_INVALID(400, "e0005", "고객님의 아이디가 틀렸습니다"),
    CUSTOMER_PASSWORD_INVALID(400, "e0006", "고객님의 비밀번호가 틀렸습니다.");

    private final int status;
    private final String code;
    private final String message;
}

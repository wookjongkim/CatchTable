package com.project.catchtable.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PARTNER_ALREADY_EXIST(400 ,"e0001", "해당 이메일에 대한 파트너가 이미 존재합니다."),
    USER_ALREADY_EXIST(400, "e0002", "해당 이메일에 대한 고객이 이미 존재합니다."),
    PARTNER_EMAIL_INVALID(400, "e0003", "파트터님의 아이디가 틀렸습니다"),
    PARTNER_PASSWORD_INVALID(400, "e0004", "파트터님의 비밀번호가 틀렸습니다"),
    CUSTOMER_EMAIL_INVALID(400, "e0005", "고객님의 아이디가 틀렸습니다"),
    CUSTOMER_PASSWORD_INVALID(400, "e0006", "고객님의 비밀번호가 틀렸습니다."),
    STORE_NAME_NOT_FOUND(400, "e0007", "해당 상점 이름에 해당하는 상점이 없습니다."),
    CUSTOMER_BY_PHONE_NUMBER_NOT_FOUND(400, "e0008", "해당 전화번호에 해당하는 유저가 존재하지 않습니다."),
    STORE_NOT_REGISTERD(400, "e0009", "해당 아이디에 해당하는 상점이 등록되어있지 않습니다."),
    RESERVATION_NOT_FOUND(400, "e0010", "해당 예약 정보는 존재하지 않습니다.(예약을 하지 않았거나, 이미 사용한 예약의 경우"),
    ARRIVE_TO_LATE(400, "e0011", "예약 시간 10분전에 도착하지 않아 해당 예약 정보는 사용할 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}

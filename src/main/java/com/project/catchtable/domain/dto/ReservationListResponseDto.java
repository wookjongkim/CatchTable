package com.project.catchtable.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Partner에게 예약 정보들을 내려주기 위한 클래스
public class ReservationListResponseDto {
    String storeName;
    String phoneNumber;
    boolean isValid;
    LocalDateTime time;
}

package com.project.catchtable.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationListResponseDto {
    String storeName;
    String phoneNumber;
    boolean isValid;
    LocalDateTime time;
}

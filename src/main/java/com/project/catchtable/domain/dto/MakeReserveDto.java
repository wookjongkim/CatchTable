package com.project.catchtable.domain.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Customer가 예약을 위해 입력받을 값들을 포함한 클래스
public class MakeReserveDto {

    @NotBlank(message = "상점 이름을 입력해주세요.")
    private String storeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "날짜와 시간은 필수 항목입니다.")
    @ApiParam(value = "예약 날짜와 시간", example = "2023-01-01T15:30:00")
    LocalDateTime reservationTime;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "전화번호는 010-0000-0000 형식이어야 합니다.")
    @NotBlank(message = "전화번호는 필수 항목입니다.")
    String phoneNumber; // 예약하는 사람 전화번호
}


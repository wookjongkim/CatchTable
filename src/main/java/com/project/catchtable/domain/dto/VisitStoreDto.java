package com.project.catchtable.domain.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitStoreDto {

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "전화번호는 010-0000-0000 형식이어야 합니다.")
    @NotBlank(message = "예약하실 때 지정한 전화번호는 필수 항목입니다.")
    String phoneNumber;

    @NotBlank(message = "예약하신 상점의 이름을 입력해주세요.")
    String storeName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime visitTime;
}

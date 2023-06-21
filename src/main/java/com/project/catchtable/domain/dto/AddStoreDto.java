package com.project.catchtable.domain.dto;

import com.project.catchtable.domain.model.Store;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 상점을 등록하기 위해 입력받을 값들을 포함한 클래스
public class AddStoreDto {

    @NotBlank(message = "상점 이름을 입력해주세요!")
    private String name;

    @NotBlank(message = "상점 위치를 입력해주세요!")
    private String location;

    @NotBlank(message = "상점에 대해 상세히 설명해주세요!")
    private String description;

    // 숫자의 경우 NotBlank 불가능
    @NotNull(message = "상점의 거리에 대해 입력해주세요~")
    private double distanceKm;

    @NotBlank(message = "파트너님의 아이디를 입력해주세요.")
    private String email;

}

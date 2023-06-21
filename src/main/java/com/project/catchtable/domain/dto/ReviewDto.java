package com.project.catchtable.domain.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Customer가 리뷰 작성을 위해 입력할 값들을 포함한 클래스
public class ReviewDto {
    @Min(value = 1, message = "평점은 1 이상이여야 합니다.")
    @Max(value = 5, message = "평점은 5 미만이여야 합니다.")
    private int rating;
    private String content;
}

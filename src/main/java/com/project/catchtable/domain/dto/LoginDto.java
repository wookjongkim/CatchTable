package com.project.catchtable.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 로그인을 위해 입력받을 값들을 포함한 클래스
public class LoginDto {

    @NotBlank(message = "이메일을 입력해주세요!")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요!")
    private String password;
}

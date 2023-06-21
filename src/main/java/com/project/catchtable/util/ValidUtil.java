package com.project.catchtable.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidUtil {

    // 입력값들에 대한 Valid를 진행할때, 어떤 부분이 잘못되었는지 Customer에게 전달
    // ex) 이메일 형식에 맞게 작성해주세요! or 해당 입력값은 필수 입력값입니다!
    public static ResponseEntity<String> extractErrorMessages(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors().forEach(error -> {
            sb.append(error.getField() + ": " + error.getDefaultMessage() + "\n");
        });
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
}

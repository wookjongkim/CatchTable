package com.project.catchtable.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidUtil {
    public static ResponseEntity<String> extractErrorMessages(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors().forEach(error -> {
            sb.append(error.getField() + ": " + error.getDefaultMessage() + "\n");
        });
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
}

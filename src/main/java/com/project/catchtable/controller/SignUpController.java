package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.SignUpDto;

import com.project.catchtable.service.PartnerService;
import com.project.catchtable.service.CustomerService;
import com.project.catchtable.util.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final PartnerService partnerService;
    private final CustomerService customerService;

    // 파트너 회원 가입
    @PostMapping("/partner")
    public ResponseEntity<String> signUpPartner(@Valid SignUpDto signUpDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidUtil.extractErrorMessages(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        String name = partnerService.signUp(signUpDto);

        sb.append(name); sb.append("파트너님 회원 가입이 완료되었습니다 :)");
        return ResponseEntity.ok(sb.toString());
    }

    // Customer 회원 가입
    @PostMapping("/customer")
    public ResponseEntity<String> signUpCustomer(@Valid SignUpDto signUpDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ValidUtil.extractErrorMessages(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        String name = customerService.signUp(signUpDto);

        sb.append(name); sb.append("고객님 회원 가입이 완료되었습니다 :)");
        return ResponseEntity.ok(sb.toString());
    }
}

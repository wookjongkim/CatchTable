package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.SignUpDto;

import com.project.catchtable.service.PartnerService;
import com.project.catchtable.service.CustomerService;
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


    @PostMapping("/partner")
    public ResponseEntity<String> signUpPartner(@RequestBody @Valid SignUpDto signUpDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return extractErrorMessages(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        String name = partnerService.signUp(signUpDto);

        sb.append(name); sb.append("님 '파트너' 회원 가입이 완료되었습니다 :)");
        return ResponseEntity.ok(sb.toString());
    }

    @PostMapping("/customer")
    public ResponseEntity<String> signUpCustomer(@RequestBody @Valid SignUpDto signUpDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return extractErrorMessages(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        String name = customerService.signUp(signUpDto);

        sb.append(name); sb.append("님 '유저' 회원 가입이 완료되었습니다 :)");
        return ResponseEntity.ok(sb.toString());
    }


    private ResponseEntity<String> extractErrorMessages(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors().forEach(error -> {
            sb.append(error.getField() + ": " + error.getDefaultMessage() + "\n");
        });
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
}

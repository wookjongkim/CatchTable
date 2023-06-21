package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.service.CustomerService;
import com.project.catchtable.service.PartnerService;
import com.project.catchtable.util.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final CustomerService customerService;
    private final PartnerService partnerService;

    // 파트너 로그인 기능
    @PostMapping("/partner")
    public ResponseEntity<String> loginPartner(@Valid LoginDto loginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidUtil.extractErrorMessages(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        String name = partnerService.login(loginDto);

        sb.append(name); sb.append("파트너님 로그인에 성공하셨습니다 :)");

        return ResponseEntity.ok(sb.toString());
    }

    // Customer 로그인 기능
    @PostMapping("/customer")
    public ResponseEntity<String> loginCustomer(@Valid LoginDto loginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidUtil.extractErrorMessages(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        String name = customerService.login(loginDto);

        sb.append(name); sb.append("고객님 로그인에 성공하셨습니다 :)");

        return ResponseEntity.ok(sb.toString());
    }
}

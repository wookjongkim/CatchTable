package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.AddStoreDto;
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
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping("/add/shop")
    public ResponseEntity<String> registerShop(@Valid AddStoreDto addStoreDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ValidUtil.extractErrorMessages(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        String name = partnerService.registerShop(addStoreDto);

        sb.append(name); sb.append(" 파트너님 매장 등록에 성공하셨습니다 :)");

        return ResponseEntity.ok(sb.toString());
    }
}

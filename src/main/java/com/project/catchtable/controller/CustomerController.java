package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.AddStoreDto;
import com.project.catchtable.domain.dto.MakeReserveDto;
import com.project.catchtable.domain.dto.StoreListResponseDto;
import com.project.catchtable.service.CustomerService;
import com.project.catchtable.service.PartnerService;
import com.project.catchtable.util.ValidUtil;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/reservation/store")
    public ResponseEntity<String> makeStoreReserve(@Valid MakeReserveDto makeReserveDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ValidUtil.extractErrorMessages(bindingResult);
        }

        return ResponseEntity.ok(customerService.reverseStore(makeReserveDto));
    }

    @GetMapping("/list/store/{listType}")
    public ResponseEntity<List<StoreListResponseDto>> getStoreList(
            @ApiParam(value = "상점 리스트 정렬 유형: '이름순' : A, '거리순' : D, '평점순' : R", required = true)
            @PathVariable String listType){
        return ResponseEntity.ok(customerService.getStoreList(listType));
    }
}

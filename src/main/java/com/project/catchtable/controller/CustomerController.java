package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.MakeReserveDto;
import com.project.catchtable.domain.dto.ReviewDto;
import com.project.catchtable.domain.dto.StoreListResponseDto;
import com.project.catchtable.domain.dto.VisitStoreDto;
import com.project.catchtable.service.CustomerService;
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

    // Customer가 해당 상점에 대해 예약을 진행
    @PostMapping("/reservation/store")
    public ResponseEntity<String> makeStoreReserve(@Valid MakeReserveDto makeReserveDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ValidUtil.extractErrorMessages(bindingResult);
        }

        return ResponseEntity.ok(customerService.reserveStore(makeReserveDto));
    }

    // Customer가 이름순, 거리순, 평점 순으로 상점 리스트들을 조회
    @GetMapping("/list/store/{listType}")
    public ResponseEntity<List<StoreListResponseDto>> getStoreList(
            @ApiParam(value = "상점 리스트 정렬 유형: '이름순' : A, '거리순' : D, '평점순' : R", required = true)
            @PathVariable String listType){
        return ResponseEntity.ok(customerService.getStoreList(listType));
    }

    // 사용자가 키오스크에 도착 정보를 전달하는 용도
    @PutMapping("/reservation/visit")
    public ResponseEntity<String> customerVisit(@Valid VisitStoreDto visitStoreDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ValidUtil.extractErrorMessages(bindingResult);
        }

        return ResponseEntity.ok(customerService.visitComplete(visitStoreDto));
    }

    // 사용한 예약에 대해 리뷰를 남기는 기능
    @PostMapping("/reservation/{reservationId}/review")
    public ResponseEntity<String> makeReview(@PathVariable Long reservationId, ReviewDto reviewDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ValidUtil.extractErrorMessages(bindingResult);
        }
        return ResponseEntity.ok(customerService.registerReview(reservationId, reviewDto));
    }

}

package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.AddStoreDto;
import com.project.catchtable.domain.dto.ReservationListResponseDto;
import com.project.catchtable.domain.model.Partner;
import com.project.catchtable.domain.model.Reservation;
import com.project.catchtable.domain.model.Store;
import com.project.catchtable.service.PartnerService;
import com.project.catchtable.util.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    // 파트너가 본인의 상점을 등록
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

    // 파트너가 본인 상점들에 존재하는 예약리스트들을 조회
    @GetMapping("/reservation/list")
    public ResponseEntity<List<ReservationListResponseDto>> getReservations(String email){
        return ResponseEntity.ok(partnerService.getReservationList(email));
    }

    // 파트너가 특정 예약에 대해 거절하는 기능
    @PutMapping("/reservation/refuse")
    public ResponseEntity<String> refuseReservation(Long reservationId){
        return ResponseEntity.ok(partnerService.refuseReservation(reservationId));
    }
}

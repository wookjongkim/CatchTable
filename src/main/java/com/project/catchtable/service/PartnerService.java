package com.project.catchtable.service;

import com.project.catchtable.domain.dto.AddStoreDto;
import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.domain.dto.ReservationListResponseDto;
import com.project.catchtable.domain.dto.SignUpDto;
import com.project.catchtable.domain.model.Partner;

import java.util.List;
import java.util.Optional;

public interface PartnerService {
    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
    String registerShop(AddStoreDto addStoreDto);

    List<ReservationListResponseDto> getReservationList(String email);
}

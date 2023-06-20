package com.project.catchtable.service;

import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.domain.dto.MakeReserveDto;
import com.project.catchtable.domain.dto.SignUpDto;
import com.project.catchtable.domain.dto.StoreListResponseDto;

import java.util.List;

public interface CustomerService {
    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
    String reverseStore(MakeReserveDto makeReserveDto);

    List<StoreListResponseDto> getStoreList(String listType);
}

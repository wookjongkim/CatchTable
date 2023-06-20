package com.project.catchtable.service;

import com.project.catchtable.domain.dto.AddStoreDto;
import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.domain.dto.SignUpDto;

public interface PartnerService {
    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
    String registerShop(AddStoreDto addStoreDto);
}

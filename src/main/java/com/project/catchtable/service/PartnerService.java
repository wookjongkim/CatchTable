package com.project.catchtable.service;

import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.domain.dto.SignUpDto;
import com.project.catchtable.domain.model.Partner;

public interface PartnerService {
    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
}

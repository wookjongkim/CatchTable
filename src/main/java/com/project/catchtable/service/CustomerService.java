package com.project.catchtable.service;

import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.domain.dto.SignUpDto;

public interface CustomerService {
    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
}

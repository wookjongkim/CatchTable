package com.project.catchtable.service;

import com.project.catchtable.domain.dto.SignUpDto;
import com.project.catchtable.domain.model.Partner;

public interface PartnerService {
    public String signUp(SignUpDto signUpDto);
}

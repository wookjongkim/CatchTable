package com.project.catchtable.service.impl;

import com.project.catchtable.domain.dto.SignUpDto;
import com.project.catchtable.domain.model.Partner;
import com.project.catchtable.exception.BusinessException;
import com.project.catchtable.exception.ErrorCode;
import com.project.catchtable.repository.PartnerRepository;
import com.project.catchtable.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    @Override
    public String signUp(SignUpDto signUpDto) {
        if(partnerRepository.existsByEmail(signUpDto.getEmail())){
            throw new BusinessException(ErrorCode.PARTNER_ALREADY_EXIST);
        }

        Partner partner = Partner.from(signUpDto);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        partner.setPassword(encoder.encode(partner.getPassword()));

        partnerRepository.save(partner);
        return partner.getName();
    }
}

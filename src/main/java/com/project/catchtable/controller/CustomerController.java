package com.project.catchtable.controller;

import com.project.catchtable.domain.dto.AddStoreDto;
import com.project.catchtable.service.CustomerService;
import com.project.catchtable.service.PartnerService;
import com.project.catchtable.util.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;


}

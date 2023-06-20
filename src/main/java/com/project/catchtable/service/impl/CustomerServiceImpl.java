package com.project.catchtable.service.impl;

import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.domain.dto.SignUpDto;
import com.project.catchtable.domain.model.Customer;
import com.project.catchtable.exception.BusinessException;
import com.project.catchtable.exception.ErrorCode;
import com.project.catchtable.repository.CustomerRepository;
import com.project.catchtable.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signUp(SignUpDto signUpDto) {
        if(customerRepository.existsByEmail(signUpDto.getEmail())){
            throw new BusinessException(ErrorCode.USER_ALREADY_EXIST);
        }

        Customer customer = Customer.from(signUpDto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customerRepository.save(customer);
        return customer.getName();
    }

    @Override
    public String login(LoginDto loginDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(loginDto.getEmail());

        if(optionalCustomer.isEmpty()){
            throw new BusinessException(ErrorCode.CUSTOMER_EMAIL_INVALID);
        }

        Customer customer = optionalCustomer.get();

        if(!passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())){
            throw new BusinessException(ErrorCode.CUSTOMER_PASSWORD_INVALID);
        }

        return customer.getName();
    }
}

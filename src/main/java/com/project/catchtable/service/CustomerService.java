package com.project.catchtable.service;

import com.project.catchtable.domain.dto.*;

import java.util.List;

public interface CustomerService {
    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
    String reserveStore(MakeReserveDto makeReserveDto);
    List<StoreListResponseDto> getStoreList(String listType);
    String visitComplete(VisitStoreDto visitStoreDto);
    String registerReview(Long reservationId, ReviewDto reviewDto);
}

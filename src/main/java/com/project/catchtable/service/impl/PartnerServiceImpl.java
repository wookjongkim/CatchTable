package com.project.catchtable.service.impl;

import com.project.catchtable.domain.dto.AddStoreDto;
import com.project.catchtable.domain.dto.LoginDto;
import com.project.catchtable.domain.dto.ReservationListResponseDto;
import com.project.catchtable.domain.dto.SignUpDto;
import com.project.catchtable.domain.model.Partner;
import com.project.catchtable.domain.model.Reservation;
import com.project.catchtable.domain.model.Store;
import com.project.catchtable.exception.BusinessException;
import com.project.catchtable.exception.ErrorCode;
import com.project.catchtable.repository.PartnerRepository;
import com.project.catchtable.repository.ReservationRepository;
import com.project.catchtable.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public String login(LoginDto loginDto) {
        Optional<Partner> optionalPartner = partnerRepository.findByEmail(loginDto.getEmail());

        if(optionalPartner.isEmpty()){
            throw new BusinessException(ErrorCode.PARTNER_EMAIL_INVALID);
        }

        Partner partner = optionalPartner.get();

        if(!passwordEncoder.matches(loginDto.getPassword(), partner.getPassword())){
            throw new BusinessException(ErrorCode.PARTNER_PASSWORD_INVALID);
        }
        return partner.getName();
    }

    @Override
    public String registerShop(AddStoreDto addStoreDto) {
        Optional<Partner> optionalPartner = partnerRepository.findByEmail(addStoreDto.getEmail());

        if(optionalPartner.isEmpty()){
            throw new BusinessException(ErrorCode.PARTNER_EMAIL_INVALID);
        }

        Partner partner = optionalPartner.get();
        Store store = Store.from(addStoreDto);
        store.setPartner(partner);

        // Partner 엔티티의 CascadeAll로 인해 해당 store_list에 store를 추가하기만 해도 자동으로 shop까지 저장됨
        partner.getStoreList().add(store);
        partnerRepository.save(partner);

        return partner.getName();
    }

    @Override
    public List<ReservationListResponseDto> getReservationList(String email) {
        // 이메일을 바탕으로 해당하는 파트너 찾기
        Partner partner = partnerRepository.findByEmail(email).get();

        // 파트너가 관리하는 모든 상점을 조회
        List<Store> stores = partner.getStoreList();

        if(stores.size() == 0){
            throw new BusinessException(ErrorCode.STORE_NOT_REGISTERD);
        }
        List<ReservationListResponseDto> result = getReservationsByStores(stores);

        // Time 기준으로 오름차순 정렬
        result.sort(Comparator.comparing(ReservationListResponseDto::getTime));
        return result;
    }

    private List<ReservationListResponseDto> getReservationsByStores(List<Store> stores) {
        List<ReservationListResponseDto> result = new ArrayList<>();

        for(Store store : stores){
            List<Reservation> reservationList = store.getReservationList();
            for(Reservation reservation : reservationList){
                ReservationListResponseDto dto = new ReservationListResponseDto(
                        store.getName(),
                        reservation.getPhoneNumber(),
                        reservation.isValid(),
                        reservation.getTime()
                );
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public String refuseReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.setRefused(true);
        reservationRepository.save(reservation);
        return "예약이 거절되었습니다.";
    }
}

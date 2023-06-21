package com.project.catchtable.service.impl;

import com.project.catchtable.domain.dto.*;
import com.project.catchtable.domain.model.Customer;
import com.project.catchtable.domain.model.Reservation;
import com.project.catchtable.domain.model.Review;
import com.project.catchtable.domain.model.Store;
import com.project.catchtable.exception.BusinessException;
import com.project.catchtable.exception.ErrorCode;
import com.project.catchtable.repository.CustomerRepository;
import com.project.catchtable.repository.ReservationRepository;
import com.project.catchtable.repository.ReviewRepository;
import com.project.catchtable.repository.StoreRepository;
import com.project.catchtable.service.CustomerService;
import com.project.catchtable.type.StoreListType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    private final ReservationRepository reservationRepository;

    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    // Customer 회원가입을 진행하는 메서드
    @Override
    public String signUp(SignUpDto signUpDto) {
        // 입력한 이메일에 해당 하는 유저가 이미 존재할 시 Exception 발생
        if(customerRepository.existsByEmail(signUpDto.getEmail())){
            throw new BusinessException(ErrorCode.USER_ALREADY_EXIST);
        }

        // 존재하지 않는다면 사용자가 입력한 비밀번호를 암호화 한뒤에 DB에 저장
        Customer customer = Customer.from(signUpDto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customerRepository.save(customer);
        return customer.getName();
    }

    @Override
    public String login(LoginDto loginDto) {
        // 입력한 이메일을 바탕으로 Customer 조회
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(loginDto.getEmail());

        // 입력한 이메일에 해당 하는 유저가 없는 경우
        if(optionalCustomer.isEmpty()){
            throw new BusinessException(ErrorCode.CUSTOMER_EMAIL_INVALID);
        }

        Customer customer = optionalCustomer.get();

        // 입력한 비밀번호가 일치 하지 않는 경우
        if(!passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())){
            throw new BusinessException(ErrorCode.CUSTOMER_PASSWORD_INVALID);
        }

        return customer.getName();
    }

    @Override
    public String reserveStore(MakeReserveDto makeReserveDto) {
        // 사용자가 입력한 상점 이름을 바탕으로 해당 상점을 조회
        Optional<Store> optStore = storeRepository.findByName(makeReserveDto.getStoreName());
        if(optStore.isEmpty()){
            throw new BusinessException(ErrorCode.STORE_NAME_NOT_FOUND);
        }
        Store store = optStore.get();

        // 사용자가 입력한 휴대폰 번호를 바탕으로 유저 조회
        Optional<Customer> optCustomer = customerRepository.findByPhoneNumber(makeReserveDto.getPhoneNumber());
        if(optCustomer.isEmpty()){
            throw new BusinessException(ErrorCode.CUSTOMER_BY_PHONE_NUMBER_NOT_FOUND);
        }

        Customer customer = optCustomer.get();

        // 예약 진행
        createReservation(makeReserveDto,store,customer);

        return "해당 예약이 접수되었습니다 :)";
    }

    private void createReservation(MakeReserveDto makeReserveDto, Store store, Customer customer) {

        // 예약 정보 생성
        Reservation reservation = Reservation.builder()
                .customer(customer)
                .store(store)
                .time(makeReserveDto.getReservationTime())
                .phoneNumber(customer.getPhoneNumber())
                .isValid(true)
                .isRefused(false)
                .build();

        customer.getReservationList().add(reservation);

        // customer만 저장해도 casecade.ALL로 인해 해당 Reservation 까지 자동으로 저장됨
        customerRepository.save(customer);
    }

    // 사용자가 원하는 정렬 순서(사전 순서, 거리 순서, 평점 순서)에 따라 리스트를 내려주는 메서드
    @Override
    public List<StoreListResponseDto> getStoreList(String listType) {
        List<StoreListResponseDto> storeResponseList;

        if(listType.equals(StoreListType.ALPHABET.getType())){
            storeResponseList = findAllStoresAlphabetically();
        }else if(listType.equals(StoreListType.DISTANCE.getType())) {
            storeResponseList = findAllStoresByDistance();
        }else{
            storeResponseList = findAllStoresByRating();
        }

        // 가져온 상점 정보들에 평점들을 계산한 후 추가
        storeResponseList.forEach(dto -> dto.setRatingAverage(calculateAverageRating(dto.getName())));

        // 평점 순서일 경우 평점 기반 정렬
        if(listType.equals(StoreListType.RATING.getType())){
            Collections.sort(storeResponseList, Comparator.comparing(StoreListResponseDto::getRatingAverage).reversed());
        }
        return storeResponseList;
    }

    // 해당 상점에 등록되어 있는 리뷰들의 평균을 계산하는 메서드
    private double calculateAverageRating(String storeName){
        Store store = storeRepository.findByName(storeName).get();

        return store.getReviewList().stream()
                .mapToDouble(Review :: getRating)
                .average().orElse(0.0);
    }

    private List<StoreListResponseDto> findAllStoresAlphabetically(){
        return storeToResponseDto(storeRepository.findAllByOrderByNameAsc());
    }

    private List<StoreListResponseDto> findAllStoresByDistance(){
        return storeToResponseDto(storeRepository.findAllByOrderByDistanceAsc());
    }

    private List<StoreListResponseDto> findAllStoresByRating(){
        return storeToResponseDto(storeRepository.findAll());
    }

    //
    private List<StoreListResponseDto> storeToResponseDto(List<Store> storeList){

        return storeList.stream().map(e -> StoreListResponseDto.from(e))
                        .collect(Collectors.toList());
    }

    // Customer가 예약을 한 매장에 도착한 경우
    @Override
    public String visitComplete(VisitStoreDto visitStoreDto) {
        // 폰넘버에 해당 하는 리뷰 정보들을 전부 불러옴
        List<Reservation> reservationList = reservationRepository.findAllByPhoneNumber(visitStoreDto.getPhoneNumber());

        // 폰넘버로 부터 가져온 해당 리뷰 정보들 중, 사용자가 도착했다고 입력한 매장 정보와 valid(해당 예약이 이미 사용된 경우 false)가 일치하는 Reservation 조회
        Optional<Reservation> optionalReservation = reservationList.stream()
                .filter(r -> r.getStore().getName().equals(visitStoreDto.getStoreName()) && r.isValid())
                .findFirst();

        optionalReservation.ifPresentOrElse(
                reservation -> {
                    // 해당 예약 정보가 존재하는 경우
                    if(reservation.isRefused()){
                        // 파트너에 의해 거절당한 예약의 경우
                        throw new BusinessException(ErrorCode.RESERVATION_IS_REFUSED);
                    }

                    // 사용된 것 우리 처리후 update
                    reservation.setValid(false);
                    reservationRepository.save(reservation);

                    // 방문 허용 시간 계산(예약 시간 10분전)후 현재 방문이 Accepted 될수 있는지 체크
                    LocalDateTime allowedArrivalTime = reservation.getTime().minusMinutes(10);
                    checkArrivalTime(allowedArrivalTime, visitStoreDto.getVisitTime());
                },
                () -> {
                    throw new BusinessException(ErrorCode.RESERVATION_NOT_FOUND);
                }
        );
        return "예약 정보 확인이 완료되었습니다. 즐거운 하루 되세요! :)";
    }

    private void checkArrivalTime(LocalDateTime allowedArrivalTime, LocalDateTime visitTime) {
        // 만약 10분전에 도착하지 않은 경우 Exception 발생
        if(visitTime.isAfter(allowedArrivalTime)){
            throw new BusinessException(ErrorCode.ARRIVE_TO_LATE);
        }
    }

    // 사용자의 리뷰 등록
    @Override
    public String registerReview(Long reservationId, ReviewDto reviewDto) {
        // Request에서 날라온 reservationId로 부터 해당 예약 조회
        Reservation reservation = reservationRepository.findById(reservationId).get();

        if(reservation.isValid()){
            // 사용되지 않은 예약의 경우
            throw new BusinessException(ErrorCode.RESERVATION_NOT_USED);
        }
        Customer customer = reservation.getCustomer();
        Store store = reservation.getStore();

        Review review = Review.builder()
                .customer(customer)
                .store(store)
                .reservation(reservation)
                .rating(reviewDto.getRating())
                .content(reviewDto.getContent())
                .build();

        reviewRepository.save(review);

        return makeReviewSuccessMessage(customer.getName(), store.getName());
    }

    private String makeReviewSuccessMessage(String customerName, String storeName) {
        StringBuilder sb = new StringBuilder();
        sb.append(customerName);
        sb.append("님 "); sb.append(storeName);
        sb.append("가계에 대한 리뷰를 남겨주셔서 감사합니다 :)");

        return sb.toString();
    }
}

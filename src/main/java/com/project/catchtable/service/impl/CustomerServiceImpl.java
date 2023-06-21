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

    @Override
    public String reserveStore(MakeReserveDto makeReserveDto) {
        Optional<Store> optStore = storeRepository.findByName(makeReserveDto.getStoreName());
        if(optStore.isEmpty()){
            throw new BusinessException(ErrorCode.STORE_NAME_NOT_FOUND);
        }
        Store store = optStore.get();

        Optional<Customer> optCustomer = customerRepository.findByPhoneNumber(makeReserveDto.getPhoneNumber());
        if(optCustomer.isEmpty()){
            throw new BusinessException(ErrorCode.CUSTOMER_BY_PHONE_NUMBER_NOT_FOUND);
        }

        Customer customer = optCustomer.get();

        createReservation(makeReserveDto,store,customer);

        return "해당 예약이 접수되었습니다 :)";
    }

    private void createReservation(MakeReserveDto makeReserveDto, Store store, Customer customer) {
        Reservation reservation = Reservation.builder()
                .customer(customer)
                .store(store)
                .time(makeReserveDto.getReservationTime())
                .phoneNumber(customer.getPhoneNumber())
                .isValid(true)
                .isRefused(false)
                .build();

        customer.getReservationList().add(reservation);

        customerRepository.save(customer);
    }

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
        storeResponseList.forEach(dto -> dto.setRatingAverage(calculateAverageRating(dto.getName())));

        // 평점 순서일 경우 평점 기반 정렬
        if(listType.equals(StoreListType.RATING.getType())){
            Collections.sort(storeResponseList, Comparator.comparing(StoreListResponseDto::getRatingAverage).reversed());
        }
        return storeResponseList;
    }

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

    private List<StoreListResponseDto> storeToResponseDto(List<Store> storeList){

        return storeList.stream().map(e -> StoreListResponseDto.from(e))
                        .collect(Collectors.toList());
    }

    @Override
    public String visitComplete(VisitStoreDto visitStoreDto) {
        List<Reservation> reservationList = reservationRepository.findAllByPhoneNumber(visitStoreDto.getPhoneNumber());
        Optional<Reservation> optionalReservation = reservationList.stream()
                .filter(r -> r.getStore().getName().equals(visitStoreDto.getStoreName()) && r.isValid())
                .findFirst();

        optionalReservation.ifPresentOrElse(
                reservation -> {
                    if(reservation.isRefused()){
                        // 파트너에 의해 거절당한 예약의 경우
                        throw new BusinessException(ErrorCode.RESERVATION_IS_REFUSED);
                    }

                    reservation.setValid(false);
                    reservationRepository.save(reservation);

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
        if(visitTime.isAfter(allowedArrivalTime)){
            throw new BusinessException(ErrorCode.ARRIVE_TO_LATE);
        }
    }

    @Override
    public String registerReview(Long reservationId, ReviewDto reviewDto) {
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

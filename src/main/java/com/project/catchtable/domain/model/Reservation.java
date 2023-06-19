package com.project.catchtable.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private LocalDate date; // 예약 날짜
    private LocalTime time; // 예약 시간

    private String phoneNumber; // 파트너가 예약 관련 전화시 이 번호로 전화

    // 여러 예약에 상점 하나
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    // 여러 예약에 사람 한명
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // 예약 한개에 리뷰 한개
    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
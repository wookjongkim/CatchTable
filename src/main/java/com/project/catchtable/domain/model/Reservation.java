package com.project.catchtable.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime time;

    private String phoneNumber; // Customer가 본인의 휴대폰 번호로 예약을 등록함
    private boolean isValid; // 아직 방문하지 않은 경우 true, 방문해서 사용한 경우 false
    private boolean isRefused; // 파트너에 의해 해당 예약이 취소된경우를 위한 boolean

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

package com.project.catchtable.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber; // phone_number로 저장됨

    // 유저 한명당 리뷰를 여러개 작성할 수 있고, Reservation 또한 여러개 할 수 있음
    // CascadeType.ALL일때 다음과 같음
    // Review 객체를 따로 저장하지 않고 Customer 객체에 Review 객체를 추가하고 Customer 객체를 저장하면, 이 Review 객체도 자동으로 저장

    // 고객한명에 리뷰 여러개 작성 가능
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviewList;

    // 고객 한명에 예약 여러개 가능
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservationList;
}

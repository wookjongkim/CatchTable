package com.project.catchtable.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private int rating;
    private String content;

    // 사람 한명 당 리뷰 여러개 가능
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // 예약 한개당 리뷰는 한개만 작성 가능, Review가 reservation 저장할 책임을 가짐
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // 상점 한개당 리뷰는 여러개 있을 것
    @ManyToOne
    @JoinColumn(name = "store_id")
    private  Store store;

}

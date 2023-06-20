package com.project.catchtable.domain.model;

import com.project.catchtable.domain.dto.AddStoreDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    private String name;
    private String location;
    private String description;
    private double distance;

    // 파트너 한명당 여러 가계 가질수 있음
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    // 가계 한개에 리뷰 여러개
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList;

    // 가계 한개에 예약 여러개
    // 원래 이 메서드에도 cascade를 all로 하였으나 예약 저장시 customer와 store 둘다 예약 정보를 저장하는 책임을 가지고 있었음
    // 그래서 우선 이부분에 casecadeAll 옵션을 제거하였음
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    public static Store from(AddStoreDto addStoreDto) {
        return Store.builder()
                .name(addStoreDto.getName())
                .location(addStoreDto.getLocation())
                .description(addStoreDto.getDescription())
                .distance(addStoreDto.getDistanceKm())
                .build();
    }
}

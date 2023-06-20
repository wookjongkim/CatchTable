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
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

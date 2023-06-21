package com.project.catchtable.domain.dto;

import com.project.catchtable.domain.model.Review;
import com.project.catchtable.domain.model.Store;
import lombok.*;

import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreListResponseDto {

    private String name;
    private String location;
    private String description;
    private double distance;
    private double ratingAverage;

    public static StoreListResponseDto from(Store store){

        return StoreListResponseDto.builder()
                .name(store.getName())
                .distance(store.getDistance())
                .description(store.getDescription())
                .location(store.getLocation())
                .build();
    }
}

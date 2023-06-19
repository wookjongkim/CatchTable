package com.project.catchtable.domain.model;

import lombok.*;

import javax.persistence.*;

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
    private double Distance;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
}

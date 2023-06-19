package com.project.catchtable.domain.model;

import com.project.catchtable.domain.dto.SignUpDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Partner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    // 여기서 Partner와 Store 사이 관계가 LAZY라면 Partner를 조회할때 연관된 Store 정보를 바로 로딩하지 않고,
    // 스토어 정보가 실제로 사용될때, (ex:partner.getStoreList()) 로딩한다는 것을 의미
    // Eager을 사용하면 Partner를 조회할때 Store 까지 항상 같이 로딩 됨

    // 파트너 한명 당 여러 상점을 가지게됨
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Store> storeList;

    public static Partner from(SignUpDto signUpDto){
        return Partner.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .phoneNumber(signUpDto.getPhoneNumber())
                .build();
    }
}

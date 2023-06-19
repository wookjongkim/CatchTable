package com.project.catchtable.repository;

import com.project.catchtable.domain.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    boolean existsByEmail(String email);
}

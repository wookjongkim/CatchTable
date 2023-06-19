package com.project.catchtable.repository;

import com.project.catchtable.domain.model.Customer;
import com.project.catchtable.domain.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    boolean existsByEmail(String email);

    Optional<Partner> findByEmail(String email);
}

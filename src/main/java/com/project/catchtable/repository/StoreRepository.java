package com.project.catchtable.repository;

import com.project.catchtable.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
    List<Store> findAll();
    List<Store> findAllByOrderByNameAsc();
    List<Store> findAllByOrderByDistanceAsc();
}

package com.deliorder.api.domain.store.repository;

import com.deliorder.api.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAll();
    Optional<Store> findById(Long id);
}

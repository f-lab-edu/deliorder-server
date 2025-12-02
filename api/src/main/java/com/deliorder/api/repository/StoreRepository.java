package com.deliorder.api.repository;

import com.deliorder.api.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAll();

    @EntityGraph(attributePaths = {"menus", "deliveryOptions"})
    Optional<Store> findById(Long id);
}

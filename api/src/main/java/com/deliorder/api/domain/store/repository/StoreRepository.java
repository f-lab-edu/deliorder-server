package com.deliorder.api.domain.store.repository;

import com.deliorder.api.domain.store.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    List<Store> findAll();
    Optional<Store> findById(Long id);
}

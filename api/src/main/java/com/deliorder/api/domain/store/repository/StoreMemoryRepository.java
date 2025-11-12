package com.deliorder.api.domain.store.repository;

import com.deliorder.api.domain.store.entity.Store;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class StoreMemoryRepository implements StoreRepository{

    private ConcurrentHashMap<Long, Store> store = new ConcurrentHashMap<>();
    private AtomicLong sequence = new AtomicLong(0L);

    @Override
    public List<Store> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Store> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}

package com.deliorder.api.domain.store.repository;

import com.deliorder.api.domain.store.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CategoryMemoryRepository implements CategoryRepository {

    private static ConcurrentHashMap<Long, Category> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong();

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Category save(Category category) {
        category.setId(sequence.incrementAndGet());
        store.put(category.getId(), category);
        return category;
    }

    public void clearStore() {
        store.clear();
    }
}

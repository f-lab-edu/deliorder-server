package com.deliorder.api.domain.store.repository;

import com.deliorder.api.domain.store.entity.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category save(Category category);
}

package com.deliorder.api.domain.store.service;

import com.deliorder.api.domain.store.entity.Category;
import com.deliorder.api.domain.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}

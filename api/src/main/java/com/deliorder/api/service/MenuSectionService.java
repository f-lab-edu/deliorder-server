package com.deliorder.api.service;

import com.deliorder.api.entity.MenuSection;
import com.deliorder.api.repository.MenuSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuSectionService {

    private final MenuSectionRepository repository;

    public List<MenuSection> findAllMenuList(Long storeId) {
        return repository.findByStoreIdOrderByDisplayOrder(storeId);
    }
}

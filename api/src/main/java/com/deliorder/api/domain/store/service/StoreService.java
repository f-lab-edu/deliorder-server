package com.deliorder.api.domain.store.service;

import com.deliorder.api.domain.store.entity.Store;
import com.deliorder.api.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Store findStore(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다. id=" + id));
    }
}

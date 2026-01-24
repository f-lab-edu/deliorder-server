package com.deliorder.api.service;

import com.deliorder.api.common.dto.AuthUser;
import com.deliorder.api.entity.Category;
import com.deliorder.api.entity.Store;
import com.deliorder.api.entity.User;
import com.deliorder.api.repository.StoreRepository;
import com.deliorder.api.service.command.StoreCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryService categoryService;

    public Store createStore(AuthUser authUser, StoreCreateCommand command) {
        User user = User.fromAuthUser(authUser);
        Category category = categoryService.findCategory(command.getCategoryId());

        Store store = Store.builder()
                .name(command.getName())
                .description(command.getDescription())
                .address(command.getAddress())
                .startTime(command.getStartTime())
                .endTime(command.getEndTime())
                .user(user)
                .category(category)
                .build();

        return storeRepository.save(store);
    }

    public Store findStore(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다. id=" + id));
    }
}

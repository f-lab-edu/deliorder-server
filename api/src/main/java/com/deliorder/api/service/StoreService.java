package com.deliorder.api.service;

import com.deliorder.api.common.dto.AuthUser;
import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import com.deliorder.api.entity.Category;
import com.deliorder.api.entity.Store;
import com.deliorder.api.entity.User;
import com.deliorder.api.repository.StoreRepository;
import com.deliorder.api.repository.UserRepository;
import com.deliorder.api.service.command.StoreCreateCommand;
import com.deliorder.api.service.command.StoreUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

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

    @Transactional
    public Store updateStore(Long storeId, AuthUser authUser, StoreUpdateCommand command) {
        User user = User.fromAuthUser(authUser);
        Store store = findStore(storeId);

        validateStoreOwnerId(store, user);

        if (StringUtils.hasText(command.getName())) {
            store.updateName(command.getName());
        }
        if (StringUtils.hasText(command.getDescription())) {
            store.updateDescription(command.getDescription());
        }
        if (StringUtils.hasText(command.getAddress())) {
            store.updateAddress(command.getAddress());
        }
        if (StringUtils.hasText(command.getImageUrl())) {
            store.updateImageUrl(command.getImageUrl());
        }
        if (command.getStartTime() != null) {
            store.updateStartTime(command.getStartTime());
        }
        if (command.getEndTime() != null) {
            store.updateEndTime(command.getEndTime());
        }
        if (command.getCategoryId() != null) {
            Category category = categoryService.findCategory(command.getCategoryId());
            store.updateCategory(category);
        }

        return store;
    }

    @Transactional
    public void deleteStore(Long storeId, AuthUser authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new HandledException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findByIdAndUser(storeId, user)
                .orElseThrow(() -> new HandledException(ErrorCode.STORE_NOT_FOUND));

        store.delete();
    }

    private static void validateStoreOwnerId(Store store, User user) {
        // 요청한 유저 ID가 가게 주인인지 확인
        if (!store.getUser().getId().equals(user.getId())) {
            throw new HandledException(ErrorCode.STORE_FORBIDDEN);
        }
    }
}

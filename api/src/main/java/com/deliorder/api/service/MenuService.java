package com.deliorder.api.service;

import com.deliorder.api.entity.Menu;
import com.deliorder.api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu getMenuDetail(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴 정보를 찾을 수 없습니다. id=" + menuId));
    }
}

package com.deliorder.api.api;

import com.deliorder.api.api.dto.MenuDetailResponse;
import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.entity.Menu;
import com.deliorder.api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDetailResponse>> getMenuDetail(@PathVariable("id") Long menuId) {

        Menu menu = menuService.getMenuDetail(menuId);

        MenuDetailResponse menuDetailResponse = MenuDetailResponse.from(menu);
        ApiResponse<MenuDetailResponse> responseBody = ApiResponse.success("메뉴 상세 정보 조회 성공", menuDetailResponse);

        return ResponseEntity.ok(responseBody);
    }

}

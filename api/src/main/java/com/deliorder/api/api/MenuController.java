package com.deliorder.api.api;

import com.deliorder.api.api.dto.MenuDetailResponse;
import com.deliorder.api.api.dto.PresignedUrlRequest;
import com.deliorder.api.api.dto.PresignedUrlResponse;
import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.entity.Menu;
import com.deliorder.api.service.MenuService;
import com.deliorder.api.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final S3Service s3Service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDetailResponse>> getMenuDetail(@PathVariable("id") Long menuId) {

        Menu menu = menuService.getMenuDetail(menuId);

        MenuDetailResponse menuDetailResponse = MenuDetailResponse.from(menu);
        ApiResponse<MenuDetailResponse> responseBody = ApiResponse.success("메뉴 상세 정보 조회 성공", menuDetailResponse);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/{id}/image/upload")
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> generatePresignedUrl(
            @PathVariable("id") Long menuId,
            @RequestBody @Valid PresignedUrlRequest request
            ) {
        return ResponseEntity.ok(ApiResponse.success("", s3Service.createPresignedGetUrl(menuId, request)));
    }
}

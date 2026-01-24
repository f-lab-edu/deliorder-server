package com.deliorder.api.api;

import com.deliorder.api.api.dto.*;
import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.common.dto.AuthUser;
import com.deliorder.api.entity.MenuSection;
import com.deliorder.api.entity.Store;
import com.deliorder.api.enums.UserRole;
import com.deliorder.api.service.MenuSectionService;
import com.deliorder.api.service.StoreService;
import com.deliorder.api.service.command.StoreCreateCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;
    private final MenuSectionService menuSectionService;

    @PostMapping("/v1/owner/stores")
    @Secured(UserRole.Authority.OWNER)
    public ResponseEntity<ApiResponse<StoreCreateResponse>> createStore(
            @Valid @RequestBody StoreCreateRequest request,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        StoreCreateCommand command = StoreCreateRequest.toCommand(request);
        Store store = storeService.createStore(authUser, command);
        StoreCreateResponse data = StoreCreateResponse.from(store);
        return ResponseEntity.ok(ApiResponse.success("", data));
    }

    @GetMapping("/v1/stores")
    public ResponseEntity<ApiResponse<StoreData>> getStores(@ModelAttribute StoreFilterRequest filter) {
        StoreItem store1 = StoreItem.builder()
                .id(1001L).name("하이닭").rating(4.9).reviewCount(172).deliveryFee(1400)
                .minOrderPrice(17000).distance(2.4).deliveryTime("45~60분")
                .discount(StoreItem.Discount.builder().type("INSTANT").amount(3000).build())
                .menus(Arrays.asList(
                        StoreItem.MenuPreview.builder().name("하이 반반치킨").price(19500).imageUrl("/menus/hidac_half.jpg").build()
                )).build();

        StoreItem store2 = StoreItem.builder()
                .id(1002L).name("롯데리아 남성역점").rating(4.9).reviewCount(690).deliveryFee(1500)
                .minOrderPrice(14000).distance(0.48).deliveryTime("29~44분")
                .discount(null)
                .menus(Arrays.asList(
                        StoreItem.MenuPreview.builder().name("치즈스틱").price(3600).imageUrl("/menus/lotteria_cheesestick.jpg").build()
                )).build();

        StoreData storeData = StoreData.builder()
                .stores(Arrays.asList(store1, store2))
                .nextCursor("eyJpZCI6MTAwMiwiY2F0ZWdvcnkiOiJDSElDS0VOIn0=")
                .hasNext(true)
                .build();

        ApiResponse<StoreData> responseBody = ApiResponse.success("가게 목록 조회 성공", storeData);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/v1/stores/{id}")
    public ResponseEntity<ApiResponse<StoreDetailData>> getStoreDetail(@PathVariable("id") Long storeId) {

        Store store = storeService.findStore(storeId);
        List<MenuSection> menuSections = menuSectionService.findAllMenuList(storeId);
        StoreDetailData bodyData = StoreDetailData.from(store, menuSections);
        ApiResponse<StoreDetailData> responseBody = ApiResponse.success("가게 상세 조회 성공", bodyData);

        return ResponseEntity.ok(responseBody);
    }
}

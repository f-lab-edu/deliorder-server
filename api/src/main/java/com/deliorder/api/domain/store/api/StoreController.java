package com.deliorder.api.domain.store.api;

import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.domain.store.api.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @GetMapping
    public ResponseEntity<ApiResponse<StoreData>> getStores(@ModelAttribute StoreFilterRequest filter) {
        StoreItem store1 = StoreItem.builder()
                .id(1001L).name("하이닭").rating(4.9).reviewCount(172).deliveryFee(1400)
                .minOrderPrice(17000).distance(2.4).deliveryTime("45~60분")
                .discount(StoreItem.Discount.builder().label("3,000원 즉시할인").amount(3000).build())
                .badges(Arrays.asList("배민푸드페스타", "픽업가능")).menus(Arrays.asList(
                        StoreItem.MenuPreview.builder().name("하이 반반치킨").price(19500).imageUrl("/menus/hidac_half.jpg").build()
                )).build();

        StoreItem store2 = StoreItem.builder()
                .id(1002L).name("롯데리아 남성역점").rating(4.9).reviewCount(690).deliveryFee(1500)
                .minOrderPrice(14000).distance(0.48).deliveryTime("29~44분")
                .discount(null)
                .badges(Arrays.asList("예약가능")).menus(Arrays.asList(
                        StoreItem.MenuPreview.builder().name("치즈스틱").price(3600).imageUrl("/menus/lotteria_cheesestick.jpg").build()
                )).build();

        StoreData storeData = StoreData.builder()
                .stores(Arrays.asList(store1, store2))
                .nextCursor("eyJpZCI6MTAwMiwiY2F0ZWdvcnkiOiJDSElDS0VOIn0=")
                .hasNext(true)
                .build();

        ApiResponse<StoreData> res = ApiResponse.success("가게 목록 조회 성공", storeData);

        return ResponseEntity.ok(res);
    }
}

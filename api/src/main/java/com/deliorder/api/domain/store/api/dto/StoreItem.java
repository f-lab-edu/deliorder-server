package com.deliorder.api.domain.store.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreItem {

    @Getter
    @Builder
    public static class Discount {
        private String type;  // INSTANT / COUPON
        private Integer amount;
    }

    @Getter
    @Builder
    public static class MenuPreview {
        private String name;
        private Integer price;
        private String imageUrl;
    }

    private Long id;
    private String name;
    private Double rating;
    private Integer reviewCount;
    private Integer deliveryFee;
    private Integer minOrderPrice;
    private Double distance;
    private String deliveryTime;
    private Discount discount;
    private List<MenuPreview> menus;
}

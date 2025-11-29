package com.deliorder.api.api.dto;

import com.deliorder.api.entity.Store;
import com.deliorder.api.entity.StoreStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class StoreDetailData {
    private Long id;
    private String name;
    private Double rating;
    private Integer reviewCount;
    private Integer minOrderPrice;
    private Double distance;
    private StoreStatus storeStatus;
    private String storeStatusLabel;
    private List<String> categories;
    private List<DeliveryOptionResponse> deliveryOptions;

    public static StoreDetailData from(Store store) {

        List<DeliveryOptionResponse> deliveryOptions = store.getDeliveryOptions().stream()
                .map(DeliveryOptionResponse::from)
                .collect(Collectors.toList());

        return StoreDetailData.builder()
                .id(store.getId())
                .name(store.getName())
                .rating(store.getRating())
                .reviewCount(store.getReviewCount())
                .minOrderPrice(store.getMinOrderPrice())
                .storeStatus(store.getStoreStatus())
                .storeStatusLabel(store.getStoreStatusLabel())
                .deliveryOptions(deliveryOptions)
                .build();
    }
}

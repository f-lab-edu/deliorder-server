package com.deliorder.api.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreDetailData {
    private Long id;
    private String name;
    private Double rating;
    private Integer reviewCount;
    private Integer minOrderPrice;
    private Double distance;
    private String storeStatus;
    private String storeStatusLabel;
    private List<String> categories;
    private List<DeliveryOption> deliveryOptions;
}

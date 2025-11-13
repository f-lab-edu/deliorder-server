package com.deliorder.api.domain.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Store {
    private Long id;
    private String name;
    private Double rating;
    private Integer reviewCount;
    private Integer deliveryFee;
    private Integer minOrderPrice;
    private String discountType;
    private Integer discountAmount;
    private String storeStatus;
    private String storeStatusLabel;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<DeliveryOption> deliveryOptions;
}

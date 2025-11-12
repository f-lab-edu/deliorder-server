package com.deliorder.api.domain.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class DeliveryOption {

    private Long id;
    private Long storeId;

    private String type;
    private String label;
    private Integer originalFee;
    private Integer discountedFee;
    private Boolean isDiscounted;
}

package com.deliorder.api.domain.store.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryOption {
    private String type;
    private String label;
    private Integer originalFee;
    private Integer discountedFee;
    private Boolean isDiscounted;
    private String badge;
    private String description;
}

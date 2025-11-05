package com.deliorder.api.domain.store.api.dto;

import com.deliorder.api.domain.store.api.type.PickupType;
import com.deliorder.api.domain.store.api.type.StoreSortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class StoreFilterRequest {
    private Long categoryId;
    private StoreSortType sort;
    private Boolean isFreeDelivery;
    private Boolean hasInstantDiscount;
    private PickupType pickupType;
    private Integer maxDeliveryTip;
    private Integer minOrderPrice;
    private Double minRating;
}

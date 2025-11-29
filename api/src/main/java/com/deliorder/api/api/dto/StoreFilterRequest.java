package com.deliorder.api.api.dto;

import com.deliorder.api.entity.DeliveryType;
import com.deliorder.api.api.type.StoreSortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StoreFilterRequest {
    private Long categoryId;
    private StoreSortType sort;
    private Boolean isFreeDelivery;
    private Boolean hasInstantDiscount;
    private DeliveryType deliveryType;
    private Integer maxDeliveryTip;
    private Integer minOrderPrice;
    private Double minRating;
}

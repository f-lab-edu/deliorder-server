package com.deliorder.api.api.dto;

import com.deliorder.api.entity.DeliveryOption;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryOptionDto {
    private String type;
    private String label;
    private Integer originalFee;
    private Integer discountedFee;
    private Boolean isDiscounted;

    public static DeliveryOptionDto from(DeliveryOption e) {
        return DeliveryOptionDto.builder()
                .type(e.getType().name())
                .label(e.getLabel())
                .originalFee(e.getOriginalFee())
                .discountedFee(e.getDiscountedFee())
                .isDiscounted(e.isDiscounted())
                .build();
    }
}

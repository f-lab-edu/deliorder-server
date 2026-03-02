package com.deliorder.api.entity;

import com.deliorder.api.common.entity.BaseEntity;
import com.deliorder.api.enums.DeliveryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeliveryOption extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    private DeliveryType type;

    private String label;
    private Integer originalFee;
    private Integer discountedFee;

    public boolean isDiscounted() {
        return discountedFee != null && discountedFee < originalFee;
    }
}

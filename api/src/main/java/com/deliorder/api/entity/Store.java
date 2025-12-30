package com.deliorder.api.entity;

import com.deliorder.api.common.entity.BaseEntity;
import com.deliorder.api.enums.DiscountType;
import com.deliorder.api.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private Double rating;
    private Integer reviewCount;

    private Integer minOrderPrice;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Integer discountAmount;

    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    private String storeStatusLabel;

    private String address;

    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<DeliveryOption> deliveryOptions = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();


}

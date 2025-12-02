package com.deliorder.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

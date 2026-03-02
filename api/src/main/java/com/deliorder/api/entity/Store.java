package com.deliorder.api.entity;

import com.deliorder.api.common.entity.BaseEntity;
import com.deliorder.api.enums.DiscountType;
import com.deliorder.api.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {

    @Column(nullable = false)
    private String name;
    private String description;
    private String imageUrl;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;

    private Double rating = 0.0;
    private Integer reviewCount = 0;

    private Integer minOrderPrice = 0;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Integer discountAmount = 0;

    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    private String storeStatusLabel;

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<DeliveryOption> deliveryOptions = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // UserRole: ROLE_OWNER

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Long getUserId() {
        return Optional.ofNullable(this.user)
                .map(BaseEntity::getId)
                .orElse(null);
    }

    public Long getCategoryId() {
        return Optional.ofNullable(this.category)
                .map(Category::getId)
                .orElse(null);
    }

    public String getCategoryLabel() {
        return Optional.ofNullable(this.category)
                .map(Category::getLabel)
                .orElse(null);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void updateEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void updateMinOrderPrice(Integer price) {
        this.minOrderPrice = price;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}

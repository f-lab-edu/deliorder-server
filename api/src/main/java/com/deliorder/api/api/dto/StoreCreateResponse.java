package com.deliorder.api.api.dto;

import com.deliorder.api.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCreateResponse {

    private Long id;
    private Long userId;
    private Long categoryId;
    private String categoryLabel;
    private String name;
    private String description;
    private String imageUrl;
    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer minOrderPrice;
    private LocalDateTime createdAt;

    public static StoreCreateResponse from(Store store) {
        return StoreCreateResponse.builder()
                .id(store.getId())
                .userId(store.getUserId())
                .categoryId(store.getCategoryId())
                .categoryLabel(store.getCategoryLabel())
                .name(store.getName())
                .description(store.getDescription())
                .imageUrl(store.getImageUrl())
                .address(store.getAddress())
                .startTime(store.getStartTime())
                .endTime(store.getEndTime())
                .minOrderPrice(store.getMinOrderPrice())
                .createdAt(store.getCreatedAt())
                .build();
    }
}

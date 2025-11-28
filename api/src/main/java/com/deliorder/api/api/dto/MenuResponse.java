package com.deliorder.api.api.dto;

import com.deliorder.api.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuResponse {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
    private Integer reviewCount;

    public static MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .imageUrl(menu.getImgUrl())
                .build();
    }
}

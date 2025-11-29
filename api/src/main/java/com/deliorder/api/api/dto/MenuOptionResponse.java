package com.deliorder.api.api.dto;

import com.deliorder.api.entity.MenuOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuOptionResponse {
    private Long id;
    private String name;
    private Integer price;

    public static MenuOptionResponse from(MenuOption option) {
        return MenuOptionResponse.builder()
                .id(option.getId())
                .name(option.getName())
                .price(option.getPrice())
                .build();
    }
}

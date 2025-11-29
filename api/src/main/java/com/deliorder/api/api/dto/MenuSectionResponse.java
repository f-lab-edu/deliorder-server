package com.deliorder.api.api.dto;

import com.deliorder.api.entity.MenuSection;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class MenuSectionResponse {
    private Long id;
    private String name;
    private List<MenuResponse> menus;

    public static MenuSectionResponse from(MenuSection section) {
        List<MenuResponse> menuResponses = section.getMenus().stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());

        return MenuSectionResponse.builder()
                .id(section.getId())
                .name(section.getName())
                .menus(menuResponses)
                .build();
    }
}

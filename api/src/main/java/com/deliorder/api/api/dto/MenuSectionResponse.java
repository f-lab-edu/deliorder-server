package com.deliorder.api.api.dto;

import com.deliorder.api.entity.Menu;
import com.deliorder.api.entity.MenuSection;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Builder
public class MenuSectionResponse {
    private Long id;
    private String name;
    private List<MenuResponse> menus;

    public static MenuSectionResponse from(MenuSection section, List<Menu> menus) {

        List<MenuResponse> menuResponses = menus.stream()
                .filter(menu -> menu.getMenuSectionId() != null)
                .filter(menu -> Objects.equals(menu.getMenuSectionId(), section.getId()))
                .map(MenuResponse::from)
                .toList();

        return MenuSectionResponse.builder()
                .id(section.getId())
                .name(section.getName())
                .menus(menuResponses)
                .build();
    }
}

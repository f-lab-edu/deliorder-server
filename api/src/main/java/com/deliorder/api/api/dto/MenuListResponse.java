package com.deliorder.api.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MenuListResponse {
    private List<MenuSectionResponse> menuSections;

    public static MenuListResponse from(List<MenuSectionResponse> menuSections) {
        return MenuListResponse.builder()
                .menuSections(menuSections)
                .build();
    }
}



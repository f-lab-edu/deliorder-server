package com.deliorder.api.api.dto;

import com.deliorder.api.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDetailResponse {
    private Long id;
    private String name;
    private Integer basePrice;
    private List<MenuOptionGroupResponse> optionGroups;

    public static MenuDetailResponse from(Menu menu) {
        List<MenuOptionGroupResponse> groupResponses = menu.getMenuOptionGroups().stream()
                .map(MenuOptionGroupResponse::from)
                .collect(Collectors.toList());

        return MenuDetailResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .basePrice(menu.getPrice())
                .optionGroups(groupResponses)
                .build();
    }
}

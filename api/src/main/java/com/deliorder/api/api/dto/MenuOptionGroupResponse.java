package com.deliorder.api.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.deliorder.api.entity.MenuOptionGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuOptionGroupResponse {
    private Long id;
    private String name;
    private String type;
    private Integer minSelect;
    private Integer maxSelect;
    private Boolean required;
    private List<MenuOptionResponse> options;

    public static MenuOptionGroupResponse from(MenuOptionGroup group) {
        List<MenuOptionResponse> optionResponses = group.getMenuOptions().stream()
                .map(MenuOptionResponse::from)
                .collect(Collectors.toList());

        return MenuOptionGroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .type(group.getType())
                .minSelect(group.getMinSelect())
                .maxSelect(group.getMaxSelect())
                .required(group.isRequired())
                .options(optionResponses)
                .build();
    }
}

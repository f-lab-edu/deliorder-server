package com.deliorder.api.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryItem {
    private final String type;
    private final String label;
    private final String iconUrl;
}

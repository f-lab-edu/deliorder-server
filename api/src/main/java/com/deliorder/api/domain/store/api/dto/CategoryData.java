package com.deliorder.api.domain.store.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryData {
    private final List<CategoryItem> categories;
}

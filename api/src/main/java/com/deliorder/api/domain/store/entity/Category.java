package com.deliorder.api.domain.store.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    private Long id;

    private String type;

    private String label;

    private String iconUrl;

    public Category(String type, String label, String iconUrl) {
        this.type = type;
        this.label = label;
        this.iconUrl = iconUrl;
    }
}

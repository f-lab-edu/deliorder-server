package com.deliorder.api.entity;

import com.deliorder.api.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    private String type;
    private String label;
    private String iconUrl;

    public Category(String type, String label, String iconUrl) {
        this.type = type;
        this.label = label;
        this.iconUrl = iconUrl;
    }
}

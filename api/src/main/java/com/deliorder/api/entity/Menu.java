package com.deliorder.api.entity;

import com.deliorder.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private Long menuSectionId;

    private String name;

    private String description;

    private int price;

    private String imgUrl;

    @OneToMany(mappedBy = "menu")
    @Builder.Default
    private List<MenuOptionGroup> menuOptionGroups = new ArrayList<>();
}

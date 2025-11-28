package com.deliorder.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_section_id", nullable = true)
    private MenuSection menuSection;

    private String name;

    private String description;

    private int price;

    private String imgUrl;

    @OneToMany(mappedBy = "menu")
    @Builder.Default
    private List<MenuOptionGroup> menuOptionGroups = new ArrayList<>();
}

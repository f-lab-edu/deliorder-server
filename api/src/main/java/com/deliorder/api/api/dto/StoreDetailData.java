package com.deliorder.api.api.dto;

import com.deliorder.api.entity.Menu;
import com.deliorder.api.entity.MenuSection;
import com.deliorder.api.entity.Store;
import com.deliorder.api.entity.StoreStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Getter
@Builder
public class StoreDetailData {
    private Long id;
    private String name;
    private Double rating;
    private Integer reviewCount;
    private Integer minOrderPrice;
    private Double distance;
    private StoreStatus storeStatus;
    private String storeStatusLabel;
    private List<String> categories;
    private List<DeliveryOptionResponse> deliveryOptions;
    private List<MenuSectionResponse> menuSections;

    public static StoreDetailData from(Store store, List<MenuSection> menuSections) {

        List<DeliveryOptionResponse> deliveryOptions = store.getDeliveryOptions().stream()
                .map(DeliveryOptionResponse::from)
                .collect(toList());

        List<Menu> menus = store.getMenus();
        List<MenuSectionResponse> menuSectionResponses = new ArrayList<>();

        if (menuSections != null && !menuSections.isEmpty()) {
            Map<Long, List<MenuResponse>> menusResponsesBySectionId = menus.stream()
                    .collect(groupingBy(
                            Menu::getMenuSectionId,
                            mapping(MenuResponse::from, toList()))
                    );

            menuSectionResponses = menuSections.stream()
                    .map(menuSection -> MenuSectionResponse.from(
                            menuSection,
                            menusResponsesBySectionId.getOrDefault(menuSection.getId(), Collections.emptyList())))
                    .toList();
        }

        return StoreDetailData.builder()
                .id(store.getId())
                .name(store.getName())
                .rating(store.getRating())
                .reviewCount(store.getReviewCount())
                .minOrderPrice(store.getMinOrderPrice())
                .storeStatus(store.getStoreStatus())
                .storeStatusLabel(store.getStoreStatusLabel())
                .deliveryOptions(deliveryOptions)
                .menuSections(menuSectionResponses)
                .build();
    }
}

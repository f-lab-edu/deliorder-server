package com.deliorder.api.domain.store.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryType {
    ALDDLE("알뜰배달"),
    SINGLE("한집배달"),
    STORE("가게배달"),
    PICKUP("픽업");

    private final String label;
}

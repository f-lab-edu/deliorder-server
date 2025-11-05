package com.deliorder.api.domain.store.api.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreSortType {
    DEFAULT("기본 정렬"),
    ORDER_COUNT_DESC("주문 많은순"),
    RATING_DESC("별점순"),
    DISTANCE_ASC("거리 가까운 순"),
    LIKE_COUNT_DESC("찜 많은 순");

    private final String label;
}

package com.deliorder.api.domain.store.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreData {
    private List<StoreItem> stores;
    private String nextCursor;
    private Boolean hasNext;
}

package com.deliorder.api.api.dto;

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

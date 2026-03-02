package com.deliorder.api.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@Builder
@RequiredArgsConstructor
public class StoreUpdateCommand {
    private final String name;
    private final String description;
    private final String imageUrl;
    private final String address;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Integer minOrderPrice;
    private final Long categoryId;
}

package com.deliorder.api.api.dto;

import com.deliorder.api.common.annotation.HalfHourOnly;
import com.deliorder.api.service.command.StoreUpdateCommand;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class StoreUpdateRequest {
    private String name;
    private String description;
    private String imageUrl;
    private String address;
    @HalfHourOnly // 00분 또는 30분 단위
    private LocalTime startTime;
    @HalfHourOnly
    private LocalTime endTime;
    @Min(value = 0, message = "최소주문금액은 0 미만일 수 없습니다.")
    private Integer minOrderPrice;
    private Long categoryId;

    public static StoreUpdateCommand toCommand(StoreUpdateRequest request) {
        return StoreUpdateCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .address(request.getAddress())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .minOrderPrice(request.getMinOrderPrice())
                .categoryId(request.getCategoryId())
                .build();
    }
}

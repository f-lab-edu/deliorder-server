package com.deliorder.api.api.dto;

import com.deliorder.api.common.annotation.HalfHourOnly;
import com.deliorder.api.service.command.StoreCreateCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCreateRequest {

    @NotBlank(message = "가게명은 필수값입니다.")
    private String name;
    @NotBlank(message = "가게 설명은 필수값입니다.")
    private String description;
    @NotBlank(message = "주소는 필수값입니다.")
    private String address;
    @NotNull(message = "시작시간은 필수값입니다.")
    @HalfHourOnly // 00분 또는 30분 단위
    private LocalTime startTime;
    @NotNull(message = "마감시간은 필수값입니다.")
    @HalfHourOnly
    private LocalTime endTime;
    @NotNull(message = "카테고리는 필수값입니다.")
    private Long categoryId;

    public static StoreCreateCommand toCommand(StoreCreateRequest request) {
        return StoreCreateCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .categoryId(request.getCategoryId())
                .build();
    }
}

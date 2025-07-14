package com.server.money_touch.domain.consumptionRecord.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class HouseholdConsumptionRequest {
    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "일일 소비 기록 등록 요청 정보")
    public static class DailyConsumptionCreateDTO {
        @Schema(description = "카테고리 이름", example = "배달/외식")
        @NotNull(message = "카테고리는 필수 선택 항목입니다.")
        private String categoryName;

        @Schema(description = "비용",example = "12000")
        @NotNull(message = "금액은 필수 입력 항목입니다.")
        @Min(value=1, message = "금액은 1원 이상이어야 합니다.")
        private Integer amount;

        @Schema(description = "항목명(최대 20자)", example = "신라방 마라탕")
        @NotNull(message = "항목명은 필수 입력 항목입니다.")
        @Size(max=20, message = "최대 20자까지만 입력할 수 있어요.")
        private String content;

        @Schema(description = "메모" , example = "마라탕 맛있었다.")
        @Size(max=1000, message = "1000자 이내로 작성해 주세요.")
        private String memo;

        @Schema(description = "소비 일시", example = "2025-07-03T18:18:00")
        private LocalDateTime consumeDate;
    }
}

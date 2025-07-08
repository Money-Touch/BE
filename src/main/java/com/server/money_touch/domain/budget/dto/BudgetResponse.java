package com.server.money_touch.domain.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BudgetResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "한 달 예산 등록 응답 정보")
    public static class BudgetCreateResultDTO {
        @Schema(description = "예산 아이디", example = "1")
        private Long budgetId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "한 달 총 소비 사용 금액 응답 정보")
    public static class TotalConsumptionResultDTO {
        @Schema(description = "한 달 총 소비 금액", example = "78000")
        private Integer totalConsumption;

        @Schema(description = "한 달 예산 대비 총 소비 금액 퍼센트", example = "50")
        private Integer percent;
    }
}

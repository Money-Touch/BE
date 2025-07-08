package com.server.money_touch.domain.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class BudgetResponse {

    @Getter
    @Schema(description = "한 달 예산 등록 응답 정보")
    public static class BudgetCreateResultDTO {
        @Schema(description = "예산 아이디", example = "1")
        private Long budgetId = 1L;
    }
}

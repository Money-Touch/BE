package com.server.money_touch.domain.budget.controller;

import com.server.money_touch.domain.budget.dto.BudgetRequest;
import com.server.money_touch.domain.budget.dto.BudgetResponse;
import com.server.money_touch.global.apiPayload.ApiResponse;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExample;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExamples;
import com.server.money_touch.global.validation.annotation.ApiSuccessCodeExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "가계부 예산 페이지", description = "가계부 예산에 관한 API")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    // 가계부 한 달 예산 등록
    @Operation(
            summary = "가계부 한 달 예산 등록 API",
            description = "가계부 한 달 예산 등록 API 입니다."
    )
    @ApiSuccessCodeExample(resultClass = BudgetResponse.BudgetCreateResultDTO.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "TOTAL_BUDGET_EXCEEDED"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "TOTAL_BUDGET_TOO_LOW"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @PostMapping()
    public ApiResponse<BudgetResponse.BudgetCreateResultDTO> postBudget(@Valid @RequestBody BudgetRequest.BudgetCreateDTO request) {
        BudgetResponse.BudgetCreateResultDTO response = null;
        return ApiResponse.onSuccess(response);
    }
}

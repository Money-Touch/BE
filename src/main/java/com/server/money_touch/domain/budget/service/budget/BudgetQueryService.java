package com.server.money_touch.domain.budget.service.budget;

import com.server.money_touch.domain.budget.dto.BudgetResponse;
import com.server.money_touch.global.validation.annotation.ExistUser;

public interface BudgetQueryService {
    // 한 달 예산 내역 조회 (소비 루틴 등록 시 나의 한 달 예산 정보를 불러오는 용도)
    BudgetResponse.BudgetDetailDTO findBudgetById(@ExistUser Long userId, Long budgetId);

    // 예산 존재 여부 검증
    Boolean existsBudgetById(Long budgetId);
}

package com.server.money_touch.domain.budget.repository.budget_category;

import com.server.money_touch.domain.budget.entity.BudgetCategory;

import java.util.List;

public interface BudgetCategoryRepositoryCustom {

    // BudgetCategory + ConsumptionCategory를 한 번에 조회
    List<BudgetCategory> findAllWithCategoryByBudgetId(Long budgetId);
}

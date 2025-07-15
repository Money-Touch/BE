package com.server.money_touch.domain.budget.repository.budget_category;

import com.server.money_touch.domain.budget.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long>, BudgetCategoryRepositoryCustom {
    List<BudgetCategory> findAllByBudgetId(Long budgetId);
}

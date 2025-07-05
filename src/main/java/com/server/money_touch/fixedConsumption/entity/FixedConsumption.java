package com.server.money_touch.fixedConsumption.entity;

import com.server.money_touch.budget.entity.BudgetCategory;
import com.server.money_touch.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class FixedConsumption extends BaseEntity {
    @Column(columnDefinition = "INT DEFAULT 0", nullable = false)
    private Integer fixedConsumptionAmount;

    @Size(max = 20)
    @Column(nullable = false, length = 60)
    private String fixedConsumptionContent;

    @Size(max = 1000)
    @Column(nullable = false, length = 3000)
    private String fixedConsumptionMemo;

    // 고정비-카테고리별 예산 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_category_id")
    private BudgetCategory budgetCategory;
}

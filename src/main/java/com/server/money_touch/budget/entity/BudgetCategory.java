package com.server.money_touch.budget.entity;

import com.server.money_touch.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class BudgetCategory extends BaseEntity {
    // 예산 카테고리 이름: 배달/외식, 교통비 등
    @Size(max = 8)
    @Column(length = 24, nullable = false)
    private String budgetCategoryName;

    // 예산 카테고리 타입: 기본 카테고리 / 내 카테고리 / 소비 루틴 카테고리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType budgetCategoryType;

    @Column(columnDefinition = "INT DEFAULT 0", nullable = false)
    private Integer budgetCategoryMoney; // 예산 카테고리 금액

    // 카테고리별 예산-예산 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;
}

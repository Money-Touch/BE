package com.server.money_touch.domain.budget.entity;

import com.server.money_touch.domain.budget.enums.CategoryType;
import com.server.money_touch.global.apiPayload.code.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ConsumptionCategory extends BaseEntity {
    // 예산 카테고리 이름: 배달/외식, 교통비 등
    @Column(length = 8, nullable = false)
    private String budgetCategoryName;

    // 예산 카테고리 타입: 기본 카테고리 / 내 카테고리 / 소비 루틴 카테고리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType budgetCategoryType;
}

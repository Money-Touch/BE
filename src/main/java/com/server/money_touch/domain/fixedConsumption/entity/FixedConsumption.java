package com.server.money_touch.domain.fixedConsumption.entity;

import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class FixedConsumption extends BaseEntity {
    @Column(columnDefinition = "INT DEFAULT 0", nullable = false)
    private Integer fixedConsumptionAmount;

    @Column(nullable = false, length = 20)
    private String fixedConsumptionContent;

    @Column(nullable = false, length = 1000)
    private String fixedConsumptionMemo;

    @Column(nullable = false, length = 20)
    private String categoryName;

    // ✅ 이번 달 반영 여부
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean appliedThisMonth;

    // 고정비-유저 다대일 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateInfo(Integer amount, String content, String memo, String categoryName) {
        this.fixedConsumptionAmount = amount;
        this.fixedConsumptionContent = content;
        this.fixedConsumptionMemo = memo;
        this.categoryName = categoryName;
    }

    // ✅ 이번 달 반영 처리
    public void markAsApplied() {
        this.appliedThisMonth = true;
    }

    // ✅ 초기화 처리 (스케줄러에서 사용)
    public void resetAppliedFlag() {
        this.appliedThisMonth = false;
    }
}

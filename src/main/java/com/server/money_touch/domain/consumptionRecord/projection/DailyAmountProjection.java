package com.server.money_touch.domain.consumptionRecord.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 가계부 달력 월별 소비 금액 조회 - 특정 날짜의 소비 내역 조회용 QueryDSL 프로젝션
@Getter
@NoArgsConstructor
public class DailyAmountProjection {

    private java.sql.Date date;
    private Integer totalAmount;

    public LocalDate getLocalDate() {
        return date.toLocalDate();  // 필요할 때 변환
    }
}

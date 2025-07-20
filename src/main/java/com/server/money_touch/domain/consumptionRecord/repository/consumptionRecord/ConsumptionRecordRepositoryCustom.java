package com.server.money_touch.domain.consumptionRecord.repository.consumptionRecord;

import com.server.money_touch.domain.consumptionRecord.projection.DailyAmountProjection;
import com.server.money_touch.domain.consumptionRecord.projection.DailyConsumptionItemProjection;

import java.time.LocalDate;
import java.util.List;

public interface ConsumptionRecordRepositoryCustom {
    // 사용자의 특정 날짜에 해당하는 소비 기록 목록을 조회
    List<DailyConsumptionItemProjection> findDailyConsumptionItems(Long userId, LocalDate date);

    // 특정 유저의 날짜별 소비 금액을 문자열 날짜 기준으로 집계하여 반환
    List<DailyAmountProjection> findDailyTotalAmounts(Long userId, LocalDate startDate, LocalDate endDate);
}

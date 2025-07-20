package com.server.money_touch.domain.consumptionRecord.service;

public interface ConsumptionRecordQueryService {
    // 소비 기록 존재 여부 검증
    Boolean existsConsumptionRecordById(Long consumptionRecordId);
}

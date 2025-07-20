package com.server.money_touch.domain.consumptionRecord.service;

import com.server.money_touch.domain.consumptionRecord.dto.ConsumptionRecordResponse;
import com.server.money_touch.domain.consumptionRecord.dto.HouseholdConsumptionRequest;
import com.server.money_touch.global.validation.annotation.ExistUser;

public interface ConsumptionRecordCommandService {
    // 일일 소비 기록 등록
    ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO createDailyConsumptionRecord(@ExistUser Long userId, HouseholdConsumptionRequest.DailyConsumptionCreateDTO request);
}

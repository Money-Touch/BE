package com.server.money_touch.domain.fixedConsumption.service;

import com.server.money_touch.domain.fixedConsumption.dto.FixedConsumptionRequest;
import com.server.money_touch.domain.fixedConsumption.dto.FixedConsumptionResponse;
import com.server.money_touch.global.validation.annotation.ExistUser;

public interface FixedConsumptionCommandService {

    // 고정비 등록
    FixedConsumptionResponse.FixedConsumptionCreateResultDTO saveFixedConsumption(@ExistUser Long userId, FixedConsumptionRequest.FixedConsumptionCreateDTO request);
}

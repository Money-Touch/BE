package com.server.money_touch.domain.fixedConsumption.converter;

import com.server.money_touch.domain.fixedConsumption.dto.FixedConsumptionRequest;
import com.server.money_touch.domain.fixedConsumption.dto.FixedConsumptionResponse;
import com.server.money_touch.domain.fixedConsumption.entity.FixedConsumption;
import com.server.money_touch.domain.user.entity.User;

public class FixedConsumptionConverter {

    // 고정비 엔티티 생성
    public static FixedConsumption toFixedConsumption(User user, FixedConsumptionRequest.FixedConsumptionCreateDTO requestDTO) {
        return FixedConsumption.builder()
                .user(user)
                .categoryName(requestDTO.getCategoryName())
                .fixedConsumptionAmount(requestDTO.getAmount())
                .fixedConsumptionContent(requestDTO.getContent())
                .fixedConsumptionMemo(requestDTO.getMemo())
                .build();
    }

    // 고정비 등록 응답 생성
    public static FixedConsumptionResponse.FixedConsumptionCreateResultDTO toFixedConsumptionCreateResultDTO(Long fixedConsumptionId) {
        return FixedConsumptionResponse.FixedConsumptionCreateResultDTO.builder()
                .fixedConsumptionId(fixedConsumptionId)
                .build();
    }
}

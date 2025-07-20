package com.server.money_touch.domain.consumptionRecord.service;

import com.server.money_touch.domain.consumptionRecord.repository.consumptionRecord.ConsumptionRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ConsumptionRecordQueryServiceImpl implements ConsumptionRecordQueryService {

    private final ConsumptionRecordRepository consumptionRecordRepository;

    // 소비 기록 존재 여부 검증
    @Transactional
    @Override
    public Boolean existsConsumptionRecordById(Long consumptionRecordId) {
        return consumptionRecordRepository.findById(consumptionRecordId).isPresent();
    }
}

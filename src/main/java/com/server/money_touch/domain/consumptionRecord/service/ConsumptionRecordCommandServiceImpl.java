package com.server.money_touch.domain.consumptionRecord.service;

import com.server.money_touch.domain.consumptionRecord.converter.consumptionRecord.ConsumptionRecordConverter;
import com.server.money_touch.domain.consumptionRecord.dto.ConsumptionRecordResponse;
import com.server.money_touch.domain.consumptionRecord.dto.HouseholdConsumptionRequest;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionCategory;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import com.server.money_touch.domain.consumptionRecord.repository.consumptionCategory.ConsumptionCategoryRepository;
import com.server.money_touch.domain.consumptionRecord.repository.consumptionRecord.ConsumptionRecordRepository;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.respotiroy.user.UserRepository;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.apiPayload.exception.handler.ErrorHandler;
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
public class ConsumptionRecordCommandServiceImpl implements ConsumptionRecordCommandService {

    private final ConsumptionRecordRepository consumptionRecordRepository;
    private final ConsumptionCategoryRepository consumptionCategoryRepository;
    private final UserRepository userRepository;

    // 일일 소비 기록 등록
    @Transactional
    @Override
    public ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO createDailyConsumptionRecord(Long userId, HouseholdConsumptionRequest.DailyConsumptionCreateDTO request) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.USER_NOT_FOUND));

        // 카테고리 이름으로 유저의 소비 카테고리 테이블 조회
        ConsumptionCategory consumptionCategory = consumptionCategoryRepository.findByUserAndBudgetCategoryName(user, request.getCategoryName())
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.CONSUMPTION_CATEGORY_NAME_NOT_FOUND));

        // 소비 기록 엔티티 생성
        ConsumptionRecord dailyConsumptionRecord = ConsumptionRecordConverter.toDailyConsumptionRecord(user, consumptionCategory, request);
        dailyConsumptionRecord.setCreatedAt(request.getConsumeDate());
        dailyConsumptionRecord.setUpdatedAt(request.getConsumeDate());
        consumptionRecordRepository.save(dailyConsumptionRecord);

        Long consumptionRecordId = dailyConsumptionRecord.getId();
        log.info("일일 소비 기록 등록 완료, consumptionRecordId: {}", consumptionRecordId);
        return ConsumptionRecordConverter.toConsumptionRecordCreateResultDTO(consumptionRecordId);
    }
}

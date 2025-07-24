package com.server.money_touch.domain.fixedConsumption.service;

import com.server.money_touch.domain.fixedConsumption.converter.FixedConsumptionConverter;
import com.server.money_touch.domain.fixedConsumption.dto.FixedConsumptionRequest;
import com.server.money_touch.domain.fixedConsumption.dto.FixedConsumptionResponse;
import com.server.money_touch.domain.fixedConsumption.entity.FixedConsumption;
import com.server.money_touch.domain.fixedConsumption.repository.FixedConsumptionRepository;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.repository.user.UserRepository;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.apiPayload.exception.handler.ErrorHandler;
import com.server.money_touch.global.constants.DefaultCategoryConstants;
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
public class FixedConsumptionCommandServiceImpl implements FixedConsumptionCommandService {
    private final FixedConsumptionRepository fixedConsumptionRepository;
    private final UserRepository userRepository;

    // 고정비 등록
    @Transactional
    @Override
    public FixedConsumptionResponse.FixedConsumptionCreateResultDTO saveFixedConsumption(
            Long userId, FixedConsumptionRequest.FixedConsumptionCreateDTO request) {
        // 카테고리 이름 유효성 검사 - 고정비는 기본 소비 카테고리만 등록 가능
        if (!DefaultCategoryConstants.DEFAULT_CATEGORY_NAMES.contains(request.getCategoryName())) {
            throw new ErrorHandler(ErrorStatus.CONSUMPTION_CATEGORY_NAME_NOT_FOUND);
        }

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.USER_NOT_FOUND));

        // 고정비 생성 및 저장
        FixedConsumption fixedConsumption = FixedConsumptionConverter.toFixedConsumption(user, request);
        fixedConsumptionRepository.save(fixedConsumption);

        log.info("고정비 등록 완료 - userId: {}, fixedConsumptionId: {}", userId, fixedConsumption.getId());
        return FixedConsumptionConverter.toFixedConsumptionCreateResultDTO(fixedConsumption.getId());
    }

}

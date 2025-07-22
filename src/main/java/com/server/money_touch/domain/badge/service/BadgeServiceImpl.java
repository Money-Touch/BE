package com.server.money_touch.domain.badge.service;

import com.server.money_touch.domain.badge.converter.BadgeConverter;
import com.server.money_touch.domain.badge.dto.BadgeResponse;
import com.server.money_touch.domain.badge.entity.UserBadge;
import com.server.money_touch.domain.badge.repository.UserBadgeRepository;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.repository.user.UserRepository;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.apiPayload.exception.handler.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BadgeServiceImpl implements BadgeService {

    private final UserBadgeRepository userBadgeRepository;
    private final UserRepository userRepository;

    @Override
    public BadgeResponse.MyBadgeListResultDTO getMyBadges(Long userId) {

        // 1. 사용자 존재 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new ErrorHandler(ErrorStatus.USER_NOT_FOUND));

        // 2. 사용자가 획득한 배지 목록 조회
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);

        log.info("사용자 배지 조회 완료 - 사용자 ID: {}, 배지 개수: {}", userId, userBadges.size());

        // DTO 변환 및 반환
        return BadgeConverter.toMyBadgeListDTO(userBadges);
    }
}

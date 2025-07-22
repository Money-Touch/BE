package com.server.money_touch.domain.user.service.user;

import com.server.money_touch.domain.user.dto.UserResponse;

public interface UserQueryService {
    // User 존재 여부 검증
    Boolean existsUserById(Long userId);

    // 현재 대표 배지 조회
    UserResponse.RepresentativeBadgeResultDTO getRepresentativeBadge(Long userId);
}

package com.server.money_touch.domain.user.service.user;

import com.server.money_touch.domain.user.dto.UserResponse;

public interface UserCommandService {

    // 대표 배지 설정
    UserResponse.RepresentativeBadgeResultDTO setRepresentativeBadge(Long userId, Long badgeId);
}

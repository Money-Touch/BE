package com.server.money_touch.domain.notification.service;

import com.server.money_touch.domain.notification.dto.NotificationResponse;

public interface NotificationService {

    // 무한스크롤로 알림 목록 조회
    NotificationResponse.NotificationListDTO getNotificationList(Long userId, Integer page, Integer size);

}

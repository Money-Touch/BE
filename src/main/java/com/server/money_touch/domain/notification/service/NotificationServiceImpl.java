package com.server.money_touch.domain.notification.service;

import com.server.money_touch.domain.notification.converter.NotificationConverter;
import com.server.money_touch.domain.notification.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    /**
     * 커서 기반 무한스크롤로 알림 목록 조회
     */
    public NotificationResponse.NotificationListDTO getNotificationsByCursor(
            Long userId, Long cursorId, int size) {

    }
}

package com.server.money_touch.domain.notification.repository;

import com.server.money_touch.domain.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 커서 기반 무한스크롤을 위한 알림 조회
     * 최신순으로 정렬, 커서 ID보다 작은(이전) 알림들을 조회
     */
    @Query("SELECT n FROM Notification n " +
            "WHERE n.user.id = :userId " +
            "AND (:cursorId IS NULL OR n.id < :cursorId) " +
            "ORDER BY n.id DESC")
    Slice<Notification> findNotificationsByCursor(
            @Param("userId") Long userId,
            @Param("cursorId") Long cursorId,
            Pageable pageable);

    /**
     * 첫 페이지 조회용 (커서 없이)
     */
    @Query("SELECT n FROM Notification n " +
            "WHERE n.user.id = :userId " +
            "ORDER BY n.id DESC")
    Slice<Notification> findFirstPageNotifications(
            @Param("userId") Long userId,
            Pageable pageable);
}

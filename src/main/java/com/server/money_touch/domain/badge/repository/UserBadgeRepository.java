package com.server.money_touch.domain.badge.repository;

import com.server.money_touch.domain.badge.entity.UserBadge;
import com.server.money_touch.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

    // 사용자의 모든 배지 조회
    List<UserBadge> findByUser(User user);

}

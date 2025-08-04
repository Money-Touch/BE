package com.server.money_touch.domain.user.repository.user;

import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUser(User user);
    boolean existsByUser(User user); // 유저 기준으로 존재 여부 확인

}

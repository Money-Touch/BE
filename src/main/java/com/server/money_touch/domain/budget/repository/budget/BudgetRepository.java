package com.server.money_touch.domain.budget.repository.budget;

import com.server.money_touch.domain.budget.entity.Budget;
import com.server.money_touch.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    Optional<Budget> findByUserIdAndCreatedMonth(Long userId, String createdMonth);

    @Query("SELECT b FROM Budget b WHERE b.user = :user " +
            "AND b.createdAt BETWEEN :start AND :end " +
            "AND b.createdAt = b.updatedAt")
    Optional<Budget> findByUserAndCreatedAtBetweenAndCreatedEqualsUpdated(@Param("user") User user,
                                                                          @Param("start") LocalDateTime start,
                                                                          @Param("end") LocalDateTime end);

}

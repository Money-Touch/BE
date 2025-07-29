package com.server.money_touch.domain.consumptionRecord.repository.feed;

import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<ConsumptionRecord, Long> {

    // 피드 상세 조회를 위한 소비기록 + 유저 + 카테고리 + 이미지 fetch join
    @EntityGraph(attributePaths = {"user", "consumptionCategory", "images"})
    Optional<ConsumptionRecord> findWithAllById(Long id);

    // 조회수 증가
    @Modifying(clearAutomatically = true)
    @Query("UPDATE ConsumptionRecord c SET c.viewCount = c.viewCount + 1 WHERE c.id = :id AND c.isPublic = true")
    void incrementViewCountIfPublic(@Param("id") Long id);

}

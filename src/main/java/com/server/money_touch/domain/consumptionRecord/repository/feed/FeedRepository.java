package com.server.money_touch.domain.consumptionRecord.repository.feed;

import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<ConsumptionRecord, Long> {

    // 피드 상세 조회를 위한 소비기록 + 유저 + 카테고리 + 이미지 fetch join
    @EntityGraph(attributePaths = {"user", "consumptionCategory", "images"})
    Optional<ConsumptionRecord> findWithAllById(Long id);
}

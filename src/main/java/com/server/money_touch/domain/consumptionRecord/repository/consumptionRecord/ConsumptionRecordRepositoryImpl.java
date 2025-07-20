package com.server.money_touch.domain.consumptionRecord.repository.consumptionRecord;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.money_touch.domain.consumptionRecord.entity.QConsumptionCategory;
import com.server.money_touch.domain.consumptionRecord.entity.QConsumptionRecord;
import com.server.money_touch.domain.consumptionRecord.projection.DailyConsumptionItemProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ConsumptionRecordRepositoryImpl implements ConsumptionRecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QConsumptionRecord record = QConsumptionRecord.consumptionRecord;
    QConsumptionCategory category = QConsumptionCategory.consumptionCategory;


    /**
     * 사용자의 특정 날짜에 해당하는 소비 기록 목록을 조회합니다.
     * - 소비 기록에는 소비 금액, 내용, 카테고리명이 포함됩니다.
     * - 소비 시간(consumeDate) 기준 오름차순(= 먼저 소비된 순서)으로 정렬됩니다.
     *
     * @param userId 사용자 ID
     * @param date 조회할 날짜 (yyyy-MM-dd)
     * @return DailyConsumptionItemProjection 리스트 (DTO 형태)
     */
    @Override
    public List<DailyConsumptionItemProjection> findDailyConsumptionItems(Long userId, LocalDate date) {
        // 입력된 날짜의 시작 시간 (00:00:00)
        LocalDateTime startOfDay = date.atStartOfDay();

        // 입력된 날짜의 끝 시간 (23:59:59.999999999)
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // JPQL 기반 QueryDSL 조회
        return queryFactory
                .select(Projections.fields(
                        DailyConsumptionItemProjection.class,
                        record.id.as("consumptionRecordId"),
                        category.budgetCategoryName.as("categoryName"),
                        record.content,
                        record.amount
                ))
                .from(record)                                              // 소비 기록 테이블
                .join(record.consumptionCategory, category)               // 소비 기록과 카테고리 조인
                .where(
                        record.user.id.eq(userId),                        // 특정 유저 ID 조건
                        record.consumeDate.between(startOfDay, endOfDay) // 해당 날짜 내 소비 기록
                )
                .orderBy(record.consumeDate.asc())                        // 소비 시간 기준 오름차순 정렬
                .fetch();                                                 // 결과 조회
    }
}

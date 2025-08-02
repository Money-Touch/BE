package com.server.money_touch.domain.consumptionRecord.repository.consumptionRecord;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.money_touch.domain.consumptionRecord.entity.QConsumptionCategory;
import com.server.money_touch.domain.consumptionRecord.entity.QConsumptionRecord;
import com.server.money_touch.domain.consumptionRecord.projection.DailyAmountProjection;
import com.server.money_touch.domain.consumptionRecord.projection.DailyConsumptionItemDetailProjection;
import com.server.money_touch.domain.consumptionRecord.projection.DailyConsumptionItemProjection;
import com.server.money_touch.domain.fixedConsumption.entity.QFixedConsumption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ConsumptionRecordRepositoryImpl implements ConsumptionRecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QConsumptionRecord record = QConsumptionRecord.consumptionRecord;
    QConsumptionCategory category = QConsumptionCategory.consumptionCategory;
    QFixedConsumption fixed = QFixedConsumption.fixedConsumption;

    /**
     * 특정 날짜의 소비 내역을 커서 기반으로 페이징하여 조회합니다.
     *
     * - 기준 날짜(start ~ end) 사이의 소비 내역 중에서
     * - (consumeDate, id) 기준으로 내림차순 정렬된 데이터에 대해
     * - 커서 기반 페이징을 적용하여 Slice 형태로 반환합니다.
     *
     * @param userId 사용자 ID
     * @param start 해당 날짜의 시작 시각 (예: 2025-07-31T00:00)
     * @param end 해당 날짜의 종료 시각 (예: 2025-07-31T23:59:59)
     * @param cursorId 마지막으로 조회한 소비 기록 ID (null이면 첫 페이지)
     * @param cursorConsumeDate cursorId에 해당하는 소비 일시
     * @param pageSize 한 페이지당 데이터 개수
     * @return Slice<DailyConsumptionItemDetailProjection>
     */
    @Override
    public Slice<DailyConsumptionItemDetailProjection> findDailyConsumptionItemsWithCursor(
            Long userId, LocalDateTime start, LocalDateTime end,
            Long cursorId, LocalDateTime cursorConsumeDate, int pageSize) {

        // 기본 조건: 사용자 ID 일치 + 조회일 범위 내 소비 내역
        BooleanExpression baseCondition = record.user.id.eq(userId)
                .and(record.consumeDate.between(start, end));

        // 커서 조건: (consumeDate, id) 기준으로 이전 데이터 조회
        BooleanExpression cursorPredicate = null;
        if (cursorId != null && cursorConsumeDate != null) {
            cursorPredicate = record.consumeDate.lt(cursorConsumeDate)
                    .or(record.consumeDate.eq(cursorConsumeDate).and(record.id.lt(cursorId)));
        }

        // 쿼리 실행: 필요한 컬럼만 projection으로 조회
        List<DailyConsumptionItemDetailProjection> results = queryFactory
                .select(Projections.fields(
                        DailyConsumptionItemDetailProjection.class,
                        record.id.as("consumptionRecordId"),
                        Expressions.cases()
                                .when(record.isFixed.isTrue()).then("고정비")
                                .otherwise(category.budgetCategoryName).as("categoryName"),
                        record.content,
                        record.amount
                ))
                .from(record)
                .join(record.consumptionCategory, category)
                .where(baseCondition.and(cursorPredicate)) // 기본 조건 + 커서 조건
                .orderBy(record.consumeDate.desc(), record.id.desc()) // 최신순 정렬
                .limit(pageSize + 1) // 다음 페이지 존재 여부 확인을 위한 +1
                .fetch();

        // hasNext 판단 및 초과한 1개 제거
        boolean hasNext = results.size() > pageSize;
        if (hasNext) {
            results.remove(results.size() - 1);
        }

        // SliceImpl로 반환 (Pageable은 의미상으로만 사용)
        return new SliceImpl<>(results, PageRequest.of(0, pageSize), hasNext);
    }


    /**
     * 특정 유저의 날짜별 소비 금액을 문자열 날짜 기준으로 집계하여 반환
     */
    @Override
    public List<DailyAmountProjection> findDailyTotalAmounts(Long userId, LocalDate startDate, LocalDate endDate) {
        // 1. DATE() 함수로 날짜만 추출 (MySQL 인덱스 활용 가능성 ↑)
        Expression<LocalDate> truncatedDate = Expressions.dateTemplate(
                LocalDate.class, "DATE({0})", record.consumeDate
        );

        // 2. QueryDSL로 일별 소비 금액 집계
        return queryFactory
                .select(Projections.fields(
                        DailyAmountProjection.class,
                        Expressions.dateTemplate(java.sql.Date.class, "DATE({0})", record.consumeDate).as("date"),
                        record.amount.sum().as("totalAmount")
                ))
                .from(record)
                .where(
                        record.user.id.eq(userId),
                        record.consumeDate.between(
                                startDate.atStartOfDay(),
                                endDate.atTime(23, 59, 59)
                        )
                )
                .groupBy(Expressions.dateTemplate(java.sql.Date.class, "DATE({0})", record.consumeDate))
                .orderBy(Expressions.dateTemplate(java.sql.Date.class, "DATE({0})", record.consumeDate).asc())
                .fetch();
    }

    // 해당 월의 소비 기록을 커서 기반 무한스크롤로 조회
    @Override
    public Slice<DailyConsumptionItemProjection> findMonthlyConsumptionItems(
            Long userId, LocalDate startDate, LocalDate endDate,
            Long cursorId, LocalDateTime cursorConsumeDate, int pageSize) {

        // 조회 시작일과 종료일을 LocalDateTime으로 변환
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        // 기본 조건: 유저 ID가 일치하고, 소비일이 해당 월 범위 내인 경우
        BooleanExpression baseCondition = record.user.id.eq(userId)
                .and(record.consumeDate.between(start, end));

        // 커서 기반 페이징 조건:
        // - consumeDate가 기준 consumeDate보다 이전이거나
        // - consumeDate가 같으면 ID가 작은 데이터만 조회
        BooleanExpression cursorPredicate = null;
        if (cursorId != null && cursorConsumeDate != null) {
            cursorPredicate = record.consumeDate.lt(cursorConsumeDate)
                    .or(record.consumeDate.eq(cursorConsumeDate).and(record.id.lt(cursorId)));
        }

        // 최종 QueryDSL 쿼리 실행
        List<DailyConsumptionItemProjection> results = queryFactory
                .select(Projections.fields(
                        DailyConsumptionItemProjection.class,
                        record.id.as("consumptionRecordId"),
                        record.consumeDate,
                        Expressions.cases()
                                .when(record.isFixed.isTrue()).then("고정비")
                                .otherwise(category.budgetCategoryName).as("categoryName"),
                        record.content,
                        record.amount
                ))
                .from(record)
                .join(record.consumptionCategory, category)
                .where(baseCondition.and(cursorPredicate)) // 기본 조건 + 커서 조건
                .orderBy(record.consumeDate.desc(), record.id.desc()) // 최신순 정렬
                .limit(pageSize + 1) // 다음 페이지 존재 여부 판단을 위한 +1
                .fetch();

        // Slice 처리를 위한 hasNext 계산
        boolean hasNext = results.size() > pageSize;
        if (hasNext) {
            results.remove(results.size() - 1); // 초과한 1개 제거
        }

        // Pageable은 의미상으로만 사용하므로 offset=0 전달
        return new SliceImpl<>(results, PageRequest.of(0, pageSize), hasNext);
    }

    // 특정 소비 기록 ID에 대한 consumeDate를 조회
    @Override
    public LocalDateTime findConsumeDateById(Long recordId) {
        return queryFactory
                .select(record.consumeDate)
                .from(record)
                .where(record.id.eq(recordId))
                .fetchOne();
    }
}

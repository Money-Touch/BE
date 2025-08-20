package com.server.money_touch.domain.fixedConsumption.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.money_touch.domain.fixedConsumption.entity.FixedConsumption;
import com.server.money_touch.domain.fixedConsumption.entity.QFixedConsumption;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FixedConsumptionRepositoryImpl implements FixedConsumptionRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QFixedConsumption fixed = QFixedConsumption.fixedConsumption;

    // 커서 기반 고정비 목록 조회
    @Override
    public Slice<FixedConsumption> findFixedConsumptionsByCursor(
            Long userId, Long cursorId, int year, int month, Pageable pageable) {

        BooleanBuilder condition = new BooleanBuilder();
        condition.and(fixed.user.id.eq(userId));

        // 커서 조건
        if (cursorId != null) {
            condition.and(fixed.id.lt(cursorId));
        }

        // ✅ 해당 달보다 이후에 생성된 고정비는 보이지 않도록 필터링
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDateTime nextMonthStart = firstDay.plusMonths(1).atStartOfDay();

        condition.and(fixed.createdAt.before(nextMonthStart));

        int pageSize = pageable.getPageSize();
        List<FixedConsumption> results = queryFactory
                .selectFrom(fixed)
                .where(condition)
                .orderBy(fixed.id.desc())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = results.size() > pageSize;
        if (hasNext) {
            results.remove(pageSize);
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}

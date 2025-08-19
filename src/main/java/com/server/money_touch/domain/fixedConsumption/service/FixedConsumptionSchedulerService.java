package com.server.money_touch.domain.fixedConsumption.service;

import com.server.money_touch.domain.budget.converter.budgetCategory.BudgetCategoryConverter;
import com.server.money_touch.domain.budget.entity.Budget;
import com.server.money_touch.domain.budget.entity.BudgetCategory;
import com.server.money_touch.domain.budget.enums.CategoryType;
import com.server.money_touch.domain.budget.repository.budget.BudgetRepository;
import com.server.money_touch.domain.budget.repository.budgetCategory.BudgetCategoryRepository;
import com.server.money_touch.domain.budget.service.budget.BudgetCommandService;
import com.server.money_touch.domain.consumptionRecord.converter.consumptionRecord.ConsumptionRecordConverter;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionCategory;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import com.server.money_touch.domain.consumptionRecord.repository.consumptionCategory.ConsumptionCategoryRepository;
import com.server.money_touch.domain.consumptionRecord.repository.consumptionRecord.ConsumptionRecordRepository;
import com.server.money_touch.domain.fixedConsumption.entity.FixedConsumption;
import com.server.money_touch.domain.fixedConsumption.repository.FixedConsumptionRepository;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixedConsumptionSchedulerService {

    private final UserRepository userRepository;
    private final FixedConsumptionRepository fixedConsumptionRepository;
    private final ConsumptionCategoryRepository consumptionCategoryRepository;
    private final ConsumptionRecordRepository consumptionRecordRepository;
    private final BudgetCommandService budgetCommandService;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetRepository budgetRepository;

    /**
     * ✅ 매월 1일 00:00 실행되는 단일 스케줄러
     * 1) 모든 고정비의 appliedThisMonth 플래그를 reset(false)
     * 2) 각 유저별 고정비를 소비 기록(ConsumptionRecord)에 반영
     *    - 예산(BudgetCategory, Budget.totalAmount) 에도 금액 반영
     *
     * 👉 두 작업을 하나로 합쳐서 실행하므로
     *    reset 이 완료되지 않으면 등록도 실행되지 않는 구조
     */
    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul") // 매월 1일 00:00
    @Transactional
    public void resetAndRegisterFixedConsumptions() {
        log.info("🕛 [스케줄러] 고정비 reset + 등록 작업 시작");

        // 1. 플래그 초기화
        fixedConsumptionRepository.resetAllFlags();
        log.info("♻️ [스케줄러] 고정비 appliedThisMonth 플래그 초기화 완료");

        // 2. 모든 유저 조회 후, 각 유저 단위로 고정비 반영
        var users = userRepository.findAllWithUserDetail();
        users.forEach(this::processUserFixedConsumption);

        log.info("✅ [스케줄러] 고정비 reset + 등록 전체 작업 완료");
    }

    /**
     * ✅ 사용자 단위 고정비 반영 로직 (비동기)
     * - 이번 달에 아직 반영되지 않은 고정비만 소비 기록으로 생성
     * - BudgetCategory 및 Budget.totalAmount 업데이트
     */
    @Async("customAsyncExecutor")
    @Transactional
    public void processUserFixedConsumption(User user) {
        try {
            // 이번 달 1일 00:00
            LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();

            // 1. Budget 조회/생성 + 기본 카테고리 생성
            Budget budget = budgetCommandService.createOrFindBudgetForMonth(user);
            budgetCommandService.saveCategoryBudgetsByType(null, user, budget, CategoryType.DEFAULT);

            // 2. 기본 ConsumptionCategory 매핑 (카테고리명 → 엔티티)
            Map<String, ConsumptionCategory> categoryMap = consumptionCategoryRepository
                    .findAllByUserAndBudgetCategoryType(user, CategoryType.DEFAULT)
                    .stream()
                    .collect(Collectors.toMap(ConsumptionCategory::getBudgetCategoryName, c -> c));

            // ✅ 고정비 총액 누적용
            AtomicInteger totalFixedAmount = new AtomicInteger(0);

            // 3. 고정비 목록 순회
            fixedConsumptionRepository.findAllByUser(user).forEach(fc -> {
                String categoryName = fc.getCategoryName();
                ConsumptionCategory category = categoryMap.get(categoryName);

                if (category == null) {
                    log.warn("❌ 고정비 카테고리 매핑 실패 - userId: {}, categoryName: {}", user.getId(), categoryName);
                    return;
                }

                // ✅ 이번 달 이미 반영된 고정비라면 SKIP
                if (Boolean.TRUE.equals(fc.getAppliedThisMonth())) {
                    log.info("⚠️ 이미 이번 달 반영된 고정비 - userId={}, content={}", user.getId(), fc.getFixedConsumptionContent());
                    return;
                }

                // (1) 소비 기록 생성 및 저장
                ConsumptionRecord record =
                        ConsumptionRecordConverter.toConsumptionRecordForFix(user, category, fc, startOfMonth);
                consumptionRecordRepository.save(record);

                // (2) 예산(BudgetCategory) 반영
                BudgetCategory budgetCategory = budgetCategoryRepository
                        .findByBudgetAndConsumptionCategory(budget, category)
                        .orElseGet(() -> budgetCategoryRepository.save(
                                BudgetCategoryConverter.toBudgetCategory(budget, category, 0))
                        );

                budgetCategory.addAmount(fc.getFixedConsumptionAmount());
                budgetCategoryRepository.save(budgetCategory);

                // (3) 고정비 금액 누적
                totalFixedAmount.addAndGet(fc.getFixedConsumptionAmount());

                // (4) flag 업데이트 (이번 달 반영 완료)
                fc.markAsApplied(); // 엔티티 메서드에서 appliedThisMonth = true
                fixedConsumptionRepository.save(fc);
            });

            // 4. Budget.totalAmount 갱신
            budget.addTotalBudget(totalFixedAmount.get());
            budgetRepository.save(budget);

            log.info("✅ [스케줄러] 사용자 {} 고정비 소비 기록 및 예산 반영 완료 (totalFixedAmount={})",
                    user.getId(), totalFixedAmount.get());

        } catch (Exception e) {
            log.error("❌ [스케줄러] 사용자 {} 고정비 소비 기록 등록 실패: {}", user.getId(), e.getMessage(), e);
        }
    }
}

package com.server.money_touch.domain.budget.service.budget;

import com.server.money_touch.domain.budget.converter.budget.BudgetConverter;
import com.server.money_touch.domain.budget.dto.BudgetResponse;
import com.server.money_touch.domain.budget.entity.Budget;
import com.server.money_touch.domain.budget.entity.BudgetCategory;
import com.server.money_touch.domain.budget.enums.CategoryType;
import com.server.money_touch.domain.budget.repository.budget.BudgetRepository;
import com.server.money_touch.domain.budget.repository.budget_category.BudgetCategoryRepository;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.apiPayload.exception.handler.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BudgetQueryServiceImpl implements BudgetQueryService {
    private final BudgetRepository budgetRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;

    // 한 달 예산 내역 조회 (소비 루틴 등록 시 나의 한 달 예산 정보를 불러오는 용도)
    @Override
    public BudgetResponse.BudgetDetailDTO findBudgetById(Long userId, Long budgetId) {
        // 1. 예산 조회
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.BUDGET_NOT_FOUND));

        // 2. 예산-카테고리 목록 조회
        List<BudgetCategory> budgetCategories = budgetCategoryRepository.findAllWithCategoryByBudgetId(budgetId);

        // 3. categoryType별로 분류하여 DTO 매핑
        Map<CategoryType, List<BudgetCategory>> groupedByType = budgetCategories.stream()
                .collect(Collectors.groupingBy(bc -> bc.getConsumptionCategory().getBudgetCategoryType()));

        // 3-1. 공통 처리 함수 사용하여 DTO 변환
        List<BudgetResponse.DefaultCategoryBudgetResponse> defaultCategories =
                convertBudgetCategories(groupedByType.get(CategoryType.DEFAULT),
                        bc -> BudgetResponse.DefaultCategoryBudgetResponse.builder()
                                .categoryName(bc.getConsumptionCategory().getBudgetCategoryName())
                                .amount(bc.getBudgetCategoryMoney())
                                .build());

        List<BudgetResponse.CustomCategoryBudgetResponse> customCategories =
                convertBudgetCategories(groupedByType.get(CategoryType.CUSTOM),
                        bc -> BudgetResponse.CustomCategoryBudgetResponse.builder()
                                .categoryName(bc.getConsumptionCategory().getBudgetCategoryName())
                                .amount(bc.getBudgetCategoryMoney())
                                .build());

        List<BudgetResponse.RoutineCategoryBudgetResponse> routineCategories =
                convertBudgetCategories(groupedByType.get(CategoryType.ROUTINE_CATEGORY),
                        bc -> BudgetResponse.RoutineCategoryBudgetResponse.builder()
                                .categoryName(bc.getConsumptionCategory().getBudgetCategoryName())
                                .amount(bc.getBudgetCategoryMoney())
                                .build());

        // 4. 응답 DTO 구성
        log.info("예산 조회 완료, budgetId: {}", budgetId);
        return BudgetConverter.toBudgetDetailDTO(budget, defaultCategories, customCategories, routineCategories);
    }

    // 예산 존재 여부 검증
    @Override
    public Boolean existsBudgetById(Long budgetId) {
        return budgetRepository.findById(budgetId).isPresent();
    }

    // 반복되는 category DTO 변환 로직을 공통 메서드로 추출
    private <T> List<T> convertBudgetCategories(List<BudgetCategory> categories, Function<BudgetCategory, T> mapper) {
        return Optional.ofNullable(categories)
                .orElse(List.of())
                .stream()
                .map(mapper)
                .toList();
    }
}

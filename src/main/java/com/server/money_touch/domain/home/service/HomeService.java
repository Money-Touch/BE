package com.server.money_touch.domain.home.service;

import com.server.money_touch.domain.home.dto.HomeResponse;
import com.server.money_touch.domain.user.entity.User;

import java.util.List;

public interface HomeService {

    // 소비왕 랭킹 조회
    HomeResponse.WiseRankingResponseDTO getWeeklyWiseRanking(Long userId);

    // 소비왕 계산, 저장
    void calculateAndSaveWeeklyWiseRanking();

    // 소비 통계(전체)
    HomeResponse.ConsumptionStatisticsTopResponseDTO getTopStatistics(User user);

    // 소비 통계(그외 카테고리)
    HomeResponse.OtherCategoryStatisticsResponseDTO getOtherStatistics(User user);

    // 소비루틴 최신순으로 5개만 반환
    List<HomeResponse.RoutinePreviewDTO> getRoutinePreviewList();

    void refreshRoutinePreviewCache();
}

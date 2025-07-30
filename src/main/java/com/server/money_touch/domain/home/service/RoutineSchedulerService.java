package com.server.money_touch.domain.home.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoutineSchedulerService {

    private final HomeService homeService;

    /** 매일 낮 12시에 캐시 갱신 */
    @Scheduled(cron = "0 0 12 * * ?")
    public void refreshRoutinePreviewCache() {
        homeService.refreshRoutinePreviewCache();
        log.info("정기 스케줄러 실행 -> 소비 루틴 미리보기 데이터 캐시 갱신 완료");
    }
}

package com.server.money_touch.domain.consumptionRecord.service.feed;

import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;

public interface FeedService {

    // 피드 상세 조회
    FeedResponse.FeedDetailResultDTO getFeedDetail(Long userId, Long consumptionRecordId);

}

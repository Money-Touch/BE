package com.server.money_touch.domain.consumptionRecord.service.reaction;

import com.server.money_touch.domain.consumptionRecord.dto.FeedRequest;
import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;

public interface ReactionService {

    /**
     * 리액션 추가/변경/삭제 처리
     * - 같은 리액션 누르면 삭제
     * - 다른 리액션 누르면 타입 변경
     * - 처음 누르면 새로 등록
     */
    FeedResponse.ReactionResultDTO addOrUpdateReaction(Long userId, Long consumptionRecordId, FeedRequest.ReactionCreateDTO request);

}

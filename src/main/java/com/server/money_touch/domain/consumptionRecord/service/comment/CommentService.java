package com.server.money_touch.domain.consumptionRecord.service.comment;

import com.server.money_touch.domain.consumptionRecord.dto.FeedRequest;
import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;

public interface CommentService {

    FeedResponse.CommentResultDTO createComment(Long userId, Long consumptionRecordId, FeedRequest.CommentCreateDTO request);

}

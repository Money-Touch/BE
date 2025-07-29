package com.server.money_touch.domain.consumptionRecord.service.comment;

import com.server.money_touch.domain.consumptionRecord.dto.FeedRequest;
import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;

import java.util.List;

public interface CommentService {

    // 댓글 작성
    FeedResponse.CommentResultDTO createComment(Long userId, Long consumptionRecordId, FeedRequest.CommentCreateDTO request);

    // 댓글 목록 조회
    List<FeedResponse.CommentListDTO> getCommentList(Long userId, Long consumptionRecordId);

}

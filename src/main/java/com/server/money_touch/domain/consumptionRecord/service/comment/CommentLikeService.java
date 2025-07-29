package com.server.money_touch.domain.consumptionRecord.service.comment;

import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;

public interface CommentLikeService {

    /**
     * 댓글 좋아요 토글
     * @param userId 사용자 ID
     * @param commentId 댓글 ID
     * @return 좋아요 결과 DTO
     */
    FeedResponse.CommentLikeResultDTO addOrRemoveLike(Long userId, Long commentId);
}

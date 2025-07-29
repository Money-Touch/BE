package com.server.money_touch.domain.consumptionRecord.converter.comment;

import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;

public class CommentLikeConverter {

    /**
     * 댓글 좋아요 결과 DTO 생성
     * @param commentId 댓글 ID
     * @param likeCount 좋아요 수
     * @param liked 현재 사용자가 좋아요 눌렀는지 여부
     * @return CommentLikeResultDTO
     */
    public static FeedResponse.CommentLikeResultDTO toCommentLikeResultDTO(Long commentId, int likeCount, boolean liked) {
        return FeedResponse.CommentLikeResultDTO.builder()
                .commentId(commentId)
                .likeCount(likeCount)
                .liked(liked)
                .build();
    }
}

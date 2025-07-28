package com.server.money_touch.domain.consumptionRecord.converter.comment;

import com.server.money_touch.domain.consumptionRecord.dto.FeedRequest;
import com.server.money_touch.domain.consumptionRecord.entity.Comment;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import com.server.money_touch.domain.user.entity.User;

public class CommentConverter {

    /**
     * 댓글 등록
     * CommentCreateDTO -> Comment 엔티티
     */
    public static Comment toComment(FeedRequest.CommentCreateDTO dto, User user, ConsumptionRecord consumptionRecord) {

        return Comment.builder()
                .user(user)
                .consumptionRecord(consumptionRecord)
                .content(dto.getContent())
                .build();

    }
}

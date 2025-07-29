package com.server.money_touch.domain.consumptionRecord.converter.comment;

import com.server.money_touch.domain.consumptionRecord.dto.FeedRequest;
import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;
import com.server.money_touch.domain.consumptionRecord.entity.Comment;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import com.server.money_touch.domain.user.entity.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 부모 댓글 리스트 -> DTO 리스트
     */
    public static List<FeedResponse.CommentListDTO> toCommentListDTOs(List<Comment> parentComments, List<Long> likedCommentIds){
        return parentComments.stream()
                .map(comment -> toCommentListDTO(comment, likedCommentIds))
                .collect(Collectors.toList());
    }

    /**
     * 단일 댓글 -> DTO (대댓글 포함)
     */
    public static FeedResponse.CommentListDTO toCommentListDTO(Comment comment, List<Long> likedCommentIds) {
        return FeedResponse.CommentListDTO.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .nickname(comment.getUser().getNickname())
                .profileImgUrl(comment.getUser().getProfileImgUrl())
                .content(comment.getContent())
                .likes(comment.getLikes())
                .liked(likedCommentIds.contains(comment.getId()))
                .createdAt(comment.getCreatedAt())
                .replies(
                        comment.getReplies().stream()
                                .sorted(Comparator.comparing(Comment::getCreatedAt)) // 오래된 댓글이 가장 먼저오도록 정렬
                                .map(reply -> toCommentListDTO(reply, likedCommentIds))
                                .collect(Collectors.toList())
                )

                .build();
    }


}

package com.server.money_touch.domain.consumptionRecord.service.comment;

import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;
import com.server.money_touch.domain.consumptionRecord.entity.Comment;
import com.server.money_touch.domain.consumptionRecord.entity.CommentLike;
import com.server.money_touch.domain.consumptionRecord.repository.comment.CommentLikeRepository;
import com.server.money_touch.domain.consumptionRecord.repository.comment.CommentRepository;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.repository.user.UserRepository;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.apiPayload.exception.handler.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public FeedResponse.CommentLikeResultDTO addOrRemoveLike(Long userId, Long commentId){

        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.USER_NOT_FOUND));

        // 2. 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.COMMENT_NOT_FOUND));

        // 3. 사용자가 좋아요를 눌렀는지 확인
        Optional<CommentLike> existing = commentLikeRepository.findByUserAndComment(user, comment);

        boolean liked;
        if (existing.isPresent()) {
            // 3-1. 이미 좋아요를 누른 상태면 → 삭제
            commentLikeRepository.delete(existing.get());
            liked = false;
        } else {
            // 3-2. 좋아요 누르지 않은 상태면 → 새로 추가
            CommentLike newLike = CommentLike.builder()
                    .user(user)
                    .comment(comment)
                    .build();
            commentLikeRepository.save(newLike);
            liked = true;
        }

        // 4. 좋아요 수 재계산
        int likeCount = commentLikeRepository.countByComment(comment);
        comment.setLikes(likeCount); // likes 필드 업데이트
        commentRepository.save(comment);

        return new FeedResponse.CommentLikeResultDTO(commentId, likeCount, liked);
    }
}

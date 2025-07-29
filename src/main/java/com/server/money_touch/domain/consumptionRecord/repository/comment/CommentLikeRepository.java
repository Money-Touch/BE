package com.server.money_touch.domain.consumptionRecord.repository.comment;

import com.server.money_touch.domain.consumptionRecord.entity.Comment;
import com.server.money_touch.domain.consumptionRecord.entity.CommentLike;
import com.server.money_touch.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 유저가 해당 댓글에 좋아요를 눌렀는지 여부 확인
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    // 댓글의 좋아요 수
    int countByComment(Comment comment);

    // 특정 소비기록 내에서 유저가 좋아요 누른 댓글 ID 목록
    @Query("SELECT cl.comment.id FROM CommentLike cl WHERE cl.user.id = :userId AND cl.comment.consumptionRecord.id = :recordId")
    List<Long> findLikedCommentIdsByUserAndConsumptionRecord(@Param("userId") Long userId, @Param("recordId") Long recordId);
}

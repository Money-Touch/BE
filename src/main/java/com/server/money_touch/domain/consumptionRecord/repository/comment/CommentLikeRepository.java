package com.server.money_touch.domain.consumptionRecord.repository.comment;

import com.server.money_touch.domain.consumptionRecord.entity.Comment;
import com.server.money_touch.domain.consumptionRecord.entity.CommentLike;
import com.server.money_touch.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 유저가 해당 댓글에 좋아요를 눌렀는지 여부 확인
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    // 댓글의 좋아요 수
    int countByComment(Comment comment);
}

package com.server.money_touch.domain.consumptionRecord.repository.comment;

import com.server.money_touch.domain.consumptionRecord.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 대댓글 포함 모든 댓글 수 count
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.consumptionRecord.id = :recordId")
    int countAllByConsumptionRecordId(@Param("recordId") Long recordId);

    // 특정 소비기록의 부모 댓글 조회
    List<Comment> findAllByConsumptionRecordIdAndParentIsNullOrderByCreatedAtAsc(Long consumptionRecordId);

}

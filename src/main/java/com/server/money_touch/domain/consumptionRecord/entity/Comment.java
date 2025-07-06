package com.server.money_touch.domain.consumptionRecord.entity;

import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumption_record_id", nullable = false)
    private ConsumptionRecord consumptionRecord;

    // 대댓글을 위한 부모 댓글 id , null 이면 일반 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column(nullable = false, length = 300)
    private String content;

    @ColumnDefault("0")
    private Integer likes = 0;

    // 자식 댓글 일대다 관계
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    // 양방향 연관관계
    public void setParent(Comment parent) {
        this.parent = parent;
        if (parent != null) {
            parent.getReplies().add(this);
        }
    }
}

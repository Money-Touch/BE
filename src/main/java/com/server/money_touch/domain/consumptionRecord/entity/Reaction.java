package com.server.money_touch.domain.consumptionRecord.entity;

import com.server.money_touch.domain.consumptionRecord.enums.ReactionType;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "consumption_record_id"})
}) // 한 유저는 한 피드당 하나의 리액션만 가능
public class Reaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumption_record_id", nullable = false)
    private ConsumptionRecord consumptionRecord;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType type;

}


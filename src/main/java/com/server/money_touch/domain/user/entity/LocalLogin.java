package com.server.money_touch.domain.user.entity;

import com.server.money_touch.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class LocalLogin extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String password;

    // 회원-로컬로그인 일대일
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void encodePassword(String password) {
        this.password = password;
    }
}

package com.server.money_touch.domain.user.dto;

import com.server.money_touch.domain.badge.entity.Badge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "마이페이지 프로필 정보")
    public static class MyPageResponseDTO {
        @Schema(description = "사용자 ID", example = "1")
        private Long userId;

        @Schema(description = "닉네임", example = "라인")
        private String nickname;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
        private String profileImgUrl;

        @Schema(description = "대표 뱃지 ID", example = "1")
        private Badge badgeId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "대표 배지 설정 응답")
    public static class RepresentativeBadgeResultDTO {

        @Schema(description = "설정된 대표 배지 ID", example = "1")
        private Long badgeId;

        @Schema(description = "배지 이름", example = "절약왕")
        private String badgeName;

        @Schema(description = "배지 이미지 URL", example = "https://example.com/badge.png")
        private String badgeImageUrl;

        @Schema(description = "배지 설명", example = "똑똑소비왕 10위권 달성 시")
        private String badgeDescription;

    }
}

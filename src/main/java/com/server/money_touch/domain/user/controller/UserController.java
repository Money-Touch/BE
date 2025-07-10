package com.server.money_touch.domain.user.controller;

import com.server.money_touch.domain.user.dto.UserResponse;
import com.server.money_touch.global.apiPayload.ApiResponse;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExample;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 페이지", description = "유저에 관한 API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    // 마이페이지
    @Operation(
            summary = "마이페이지 유저 정보 조회 API",
            description = "현재 로그인한 사용자의 마이페이지 정보를 조회하는 API입니다."
    )
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_UNAUTHORIZED"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/mypage")
    public ApiResponse<UserResponse.MyPageResponseDTO> getMyPage() {
        UserResponse.MyPageResponseDTO response = UserResponse.MyPageResponseDTO.builder().build();
        return ApiResponse.onSuccess(response);
    }

    // 대표 배지 설정
    @Operation(
            summary = "대표 배지 설정 API",
            description = "획득한 배지 중에서 대표 배지를 설정하는 API입니다. UserBadge 테이블에서 획득 여부를 확인 후 User 엔티티의 badgeId를 업데이트합니다."
    )
//    @ApiSuccessCodeExample(resultClass = BadgeResponse.BadgeDetailResultDTO.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "BADGE_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "BADGE_NOT_EARNED"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR")
    })
    @Parameters({
            @Parameter(name = "badgeId", description = "설정할 대표 배지 ID", example = "1", required = true)
    })
    @PatchMapping("/representative-badge/{badgeId}")
    public ApiResponse<UserResponse.RepresentativeBadgeResultDTO> setRepresentativeBadge(
//            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long badgeId
    ) {
        UserResponse.RepresentativeBadgeResultDTO response = UserResponse.RepresentativeBadgeResultDTO.builder().build();
        return ApiResponse.onSuccess(response);
    }
}

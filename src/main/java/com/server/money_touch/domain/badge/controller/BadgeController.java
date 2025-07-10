package com.server.money_touch.domain.badge.controller;

import com.server.money_touch.domain.badge.dto.BadgeResponse;
import com.server.money_touch.global.apiPayload.ApiResponse;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExample;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배지 페이지", description = "배지에 관한 API")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/badge")
public class BadgeController {

    @Operation(
            summary = "내가 획득한 배지 목록 조회 API",
            description = "현재 로그인한 사용자가 획득한 배지 목록을 조회하는 API 입니다."
    )
//    @ApiSuccessCodeExample(resultClass = BadgeResponse.MyBadgeListResultDTO.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "BADGE_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/my")
    public ApiResponse<BadgeResponse.MyBadgeListResultDTO> getMyBadges() {
        BadgeResponse.MyBadgeListResultDTO response = BadgeResponse.MyBadgeListResultDTO.builder().build();
        return ApiResponse.onSuccess(response);
    }

}

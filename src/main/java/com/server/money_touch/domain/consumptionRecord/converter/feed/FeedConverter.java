package com.server.money_touch.domain.consumptionRecord.converter.feed;

import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionCategory;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecordImage;
import com.server.money_touch.domain.consumptionRecord.enums.ReactionType;
import com.server.money_touch.domain.user.entity.User;

import java.util.stream.Collectors;

public class FeedConverter {

    /**
     * 소비기록 → 피드 상세 DTO 변환
     * @param record 소비기록 엔티티
     * @param myReaction 현재 로그인한 사용자의 반응 타입 (WISE/WASTE/null)
     * @return FeedDetailResultDTO
     */
    public static FeedResponse.FeedDetailResultDTO toFeedDetailDTO(ConsumptionRecord record, ReactionType myReaction) {
        return FeedResponse.FeedDetailResultDTO.builder()
                .consumptionRecordId(record.getId())
                .user(toUserInfo(record.getUser()))
                .consumptionCategory(toCategoryInfo(record.getConsumptionCategory()))
                .amount(record.getAmount())
                .content(record.getContent())
                .imageUrls(record.getImages().stream()
                        .map(ConsumptionRecordImage::getFilePath)
                        .collect(Collectors.toList()))
                .memo(record.getMemo())
                .createdAt(record.getCreatedAt())
                .wiseCount(record.getWiseCount())
                .wasteCount(record.getWasteCount())
                .commentCount(record.getCommentCount())
                .viewCount(record.getViewCount())
                .myReaction(myReaction)
                .build();
    }

    // 사용자 정보 반환
    public static FeedResponse.UserInfo toUserInfo(User user) {
        return FeedResponse.UserInfo.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
    }

    // 카티고리 정보 반환
    public static FeedResponse.CategoryInfo toCategoryInfo(ConsumptionCategory category) {
        return FeedResponse.CategoryInfo.builder()
                .categoryId(category.getId())
                .budgetCategoryName(category.getBudgetCategoryName())
                .build();
    }

    // 조회수 증가 결과 반환
    public static FeedResponse.ViewCountResultDTO toViewCountDTO(ConsumptionRecord record) {
        return FeedResponse.ViewCountResultDTO.builder()
                .consumptionRecordId(record.getId())
                .viewCount(record.getViewCount())
                .build();
    }
}

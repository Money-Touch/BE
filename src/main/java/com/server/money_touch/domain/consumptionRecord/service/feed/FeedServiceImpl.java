package com.server.money_touch.domain.consumptionRecord.service.feed;

import com.server.money_touch.domain.consumptionRecord.converter.feed.FeedConverter;
import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;
import com.server.money_touch.domain.consumptionRecord.entity.ConsumptionRecord;
import com.server.money_touch.domain.consumptionRecord.entity.Reaction;
import com.server.money_touch.domain.consumptionRecord.enums.ReactionType;
import com.server.money_touch.domain.consumptionRecord.repository.feed.FeedRepository;
import com.server.money_touch.domain.consumptionRecord.repository.reaction.ReactionRepository;
import com.server.money_touch.domain.user.entity.User;
import com.server.money_touch.domain.user.repository.user.UserRepository;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.apiPayload.exception.handler.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;

    /**
     * 피드 상세 조회
     * @param userId 로그인한 사용자 ID
     * @param consumptionRecordId 조회할 소비기록 ID
     * @return FeedDetailResultDTO
     */
    public FeedResponse.FeedDetailResultDTO getFeedDetail(Long userId, Long consumptionRecordId) {

        // 1. 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new ErrorHandler(ErrorStatus.USER_NOT_FOUND));

        // 2. 소비기록 + 유저 + 카테고리 + 이미지 한번에 fetch join
        ConsumptionRecord record = feedRepository.findWithAllById(consumptionRecordId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.CONSUMPTION_RECORD_NOT_FOUND));

        // 3. 비공개 소비기록는 접근할 수 없음
        if (!record.isPublic()) {
            throw new ErrorHandler(ErrorStatus.FORBIDDEN_ACCESS_ON_PRIVATE_FEED);
        }

        // 4. 내가 남긴 리액션 조회 (null 가능)
        ReactionType myReactionType = reactionRepository.findByUserAndConsumptionRecord(user, record)
                .map(Reaction::getType)
                .orElse(null);


        // 5. 응답 변환
        return FeedConverter.toFeedDetailDTO(record, myReactionType);
    }
}

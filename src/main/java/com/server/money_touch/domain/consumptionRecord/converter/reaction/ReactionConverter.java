package com.server.money_touch.domain.consumptionRecord.converter.reaction;

import com.server.money_touch.domain.consumptionRecord.dto.FeedResponse;

public class ReactionConverter {

    public static FeedResponse.ReactionResultDTO toReactionResultDTO(Long recordId, Integer wiseCount, Integer wasteCount, String myReaction) {
        return FeedResponse.ReactionResultDTO.builder()
                .consumptionRecordId(recordId)
                .wiseCount(wiseCount)
                .wasteCount(wasteCount)
                .myReaction(myReaction)
                .build();
    }

}

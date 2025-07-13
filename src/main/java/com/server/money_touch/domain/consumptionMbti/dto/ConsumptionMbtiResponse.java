package com.server.money_touch.domain.consumptionMbti.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class ConsumptionMbtiResponse {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(description = "소비 Mbti 조회 응답 정보")
    public static class ConsumptionMbtiResultDTO{

        @Schema(description = "소비 Mbti 아이디", example = "1")
        private Long mbtiId;
    }
}

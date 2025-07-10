package com.server.money_touch.domain.consumptionRecord.controller;

import com.server.money_touch.domain.consumptionRecord.dto.ConsumptionRecordRequest;
import com.server.money_touch.domain.consumptionRecord.dto.ConsumptionRecordResponse;
import com.server.money_touch.global.apiPayload.ApiResponse;
import com.server.money_touch.global.apiPayload.code.status.ErrorStatus;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExample;
import com.server.money_touch.global.validation.annotation.ApiErrorCodeExamples;
import com.server.money_touch.global.validation.annotation.ApiSuccessCodeExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "소비 기록 등록 페이지", description = "소비 기록 등록에 관한 API")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consumptionrecord")
public class ConsumptionRecordController {

    // 소비 기록 등록
    @Operation(
            summary = "소비 기록 등록 API",
            description = "소비 기록 등록 API 입니다."
    )
    @ApiSuccessCodeExample(resultClass = ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })

    @PostMapping("/record")
    public ApiResponse<ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO> postConsumptionRecord(
            @Valid @RequestBody ConsumptionRecordRequest.ConsumptionRecordCreateDTO request){

        ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO response = null;

        return ApiResponse.onSuccess(response);
    }

    // 가계부 일일 소비 등록
    @Operation(
            summary = "가계부 - 일일 소비 등록 API",
            description = "가계부에서 일일 소비 내역을 등록하는 API 입니다."
    )
    @ApiSuccessCodeExample(resultClass = ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @PostMapping("/record/daily")
    public ApiResponse<ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO> postDailyConsumptionRecord(
            @Valid @RequestBody ConsumptionRecordRequest.DailyConsumptionCreateDTO request){

        ConsumptionRecordResponse.ConsumptionRecordCreateResultDTO response = null;
        return ApiResponse.onSuccess(response);
    }

    // 가계부 일일 소비 수정
    @Operation(
            summary = "가계부 - 일일 소비 수정 API",
            description = "가계부에서 아이디와 일치하는 일일 소비 내역을 수정하는 API 입니다."
    )
    @ApiSuccessCodeExample(resultClass = ApiResponse.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "CONSUMPTION_RECORD_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @Parameters({
            @Parameter(name = "consumptionRecordId", description = "수정하려는 소비 기록 아이디", example = "1", required = true),
    })
    @PatchMapping("/record/daily/{consumptionRecordId}")
    public ApiResponse<?> patchDailyConsumptionRecord(@Valid @RequestBody ConsumptionRecordRequest.DailyConsumptionCreateDTO request,
                                                      @PathVariable Long consumptionRecordId){

        return ApiResponse.onSuccess(null);
    }

    // 가계부 일일 소비 삭제
    @Operation(
            summary = "가계부 - 일일 소비 삭제 API",
            description = "가계부에서 아이디와 일치하는 일일 소비 내역을 삭제하는 API 입니다."
    )
    @ApiSuccessCodeExample(resultClass = ApiResponse.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "CONSUMPTION_RECORD_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @Parameters({
            @Parameter(name = "consumptionRecordId", description = "삭제하려는 소비 기록 아이디", example = "1", required = true),
    })
    @DeleteMapping("/record/daily/{consumptionRecordId}")
    public ApiResponse<?> deleteDailyConsumptionRecord(@Valid @RequestBody ConsumptionRecordRequest.DailyConsumptionCreateDTO request,
                                                       @PathVariable Long consumptionRecordId){

        return ApiResponse.onSuccess(null);
    }

    // 가계부 일일 소비 내역 조회
    @Operation(
            summary = "가계부 일일 소비 내역 조회 API",
            description = "가계부에서 아이디와 일치하는 일일 소비 내역을 조회하는 API 입니다. " +
                    "(일일 소비 내역 수정 시 정보 불러오는 용도)"
    )
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "CONSUMPTION_RECORD_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @Parameters({
            @Parameter(name = "consumptionRecordId", description = "조회하려는 소비 기록 아이디", example = "1", required = true),
    })
    @GetMapping("/record/daily/{consumptionRecordId}")
    public ApiResponse<ConsumptionRecordResponse.DailyConsumptionDetailDTO> getDailyConsumptionRecord(@PathVariable Long consumptionRecordId){

        ConsumptionRecordResponse.DailyConsumptionDetailDTO response = ConsumptionRecordResponse.DailyConsumptionDetailDTO.builder().build();
        return ApiResponse.onSuccess(response);
    }

    // 가계부 해당 월의 소비 내역 목록 조회
    @Operation(
            summary = "가계부 - 해당 월의 일일 소비 내역 목록 조회 API",
            description = "가계부에서 특정 월의 일일 소비 내역 목록을 조회하는 API입니다. 연도(year)와 월(month)을 쿼리 파라미터로 입력하세요."
    )
//    @ApiSuccessCodeExample(resultClass = ConsumptionRecordResponse.MonthlyHistoryResponseDTO.class)
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @Parameters({
            @Parameter(name = "year", description = "조회하려는 소비 연도", example = "2025", required = true),
            @Parameter(name = "month", description = "조회하려는 소비 월", example = "7", required = true),
    })
    @GetMapping("/record/monthly")
    public ApiResponse<ConsumptionRecordResponse.MonthlyHistoryResponseDTO> getConsumptionRecordByMonth(@RequestParam Integer year,
                                                                                                        @RequestParam Integer month) {
        ConsumptionRecordResponse.MonthlyHistoryResponseDTO response = ConsumptionRecordResponse.MonthlyHistoryResponseDTO.builder().build();
        return ApiResponse.onSuccess(response);
    }

    // 가계부 달력 월별 소비 금액 조회
    @Operation(
            summary = "가계부 달력 - 월별 소비 금액 조회 API",
            description = "입력한 연도(year), 월(month)에 해당하는 일별 소비 총액을 조회하는 API입니다."
    )
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @Parameters({
            @Parameter(name = "year", description = "조회하려는 소비 연도", example = "2025", required = true),
            @Parameter(name = "month", description = "조회하려는 소비 월", example = "7", required = true),
    })
    @GetMapping("/record/calendar")
    public ApiResponse<ConsumptionRecordResponse.CalendarDateAmountMapDTO> getConsumptionRecordByMonthInCalendar(@RequestParam Integer year,
                                                                                                                 @RequestParam Integer month)
    {

        ConsumptionRecordResponse.CalendarDateAmountMapDTO response = ConsumptionRecordResponse.CalendarDateAmountMapDTO.builder().build();
        return ApiResponse.onSuccess(response);
    }

    // 가계부 달력 특정 일의 소비 내역 조회
    @Operation(
            summary = "가계부 달력 - 특정 날짜 소비 내역 조회 API",
            description = "입력한 연도(year), 월(month), 일(day)에 해당하는 소비 내역을 조회합니다."
    )
    @ApiErrorCodeExamples({
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "USER_NOT_FOUND"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_BAD_REQUEST"),
            @ApiErrorCodeExample(value = ErrorStatus.class, name = "_INTERNAL_SERVER_ERROR"),
    })
    @Parameters({
            @Parameter(name = "year", description = "조회하려는 소비 연도", example = "2025", required = true),
            @Parameter(name = "month", description = "조회하려는 소비 월", example = "7", required = true),
            @Parameter(name = "day", description = "조회하려는 소비 일", example = "23", required = true),
    })
    @GetMapping("/record/calendar/daily")
    public ApiResponse<ConsumptionRecordResponse.CalendarDailyConsumeDetailDTO> getConsumptionRecordByMonthAndDayInCalendar(@RequestParam Integer year,
                                                                                                                      @RequestParam Integer month)
    {

        ConsumptionRecordResponse.CalendarDailyConsumeDetailDTO response = ConsumptionRecordResponse.CalendarDailyConsumeDetailDTO.builder().build();
        return ApiResponse.onSuccess(response);
    }
}

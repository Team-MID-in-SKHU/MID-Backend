package com.skhu.mid_skhu.app.controller.event;

import com.skhu.mid_skhu.app.dto.event.responseDto.EventSearchResponseDto;
import com.skhu.mid_skhu.app.service.event.RandomEventDisplayService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "인기 프로그램", description = "인기 프로그램을 관리하는 api그룹")
@RequestMapping("/api/v1/event")
public class RandomEventDisplayController {

    private final RandomEventDisplayService randomEventDisplayService;

    @GetMapping("/random")
    @Operation(
            summary = "랜덤 이벤트 조회",
            description = "랜덤으로 두 가지 이벤트를 조회하는 api입니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "랜덤 이벤트 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "url문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<List<EventSearchResponseDto>>> displayRandomEvents() {
        ApiResponseTemplate<List<EventSearchResponseDto>> dataList = randomEventDisplayService.displayRandomEvents();
        return ResponseEntity.status(dataList.getStatus()).body(dataList);
    }
}
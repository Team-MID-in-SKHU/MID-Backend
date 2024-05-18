package com.skhu.mid_skhu.app.controller.event;

import com.skhu.mid_skhu.app.dto.event.requestDto.EventSearchRequestDto;
import com.skhu.mid_skhu.app.dto.event.responseDto.EventSearchResponseDto;
import com.skhu.mid_skhu.app.service.event.EventSearchService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "행사/이벤트 USER용", description = "행사/이벤트를 관리하는 USER용 api그룹")
@RequestMapping("/api/v1/event")
public class EventForUserController {

    private final EventSearchService eventSearchService;

    @GetMapping("/search")
    @Operation(
            summary = "행사/이벤트 검색",
            description = "행사/이벤트를 검색하는 api입니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "행사/이벤트 검색 성공"),
                    @ApiResponse(responseCode = "403", description = "url문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<List<EventSearchResponseDto>>> searchEvents(@RequestBody EventSearchRequestDto requestDto,
                                                                                          Principal principal) {

        ApiResponseTemplate<List<EventSearchResponseDto>> dataList = eventSearchService.searchEvents(principal, requestDto);

        return ResponseEntity.status(dataList.getStatus()).body(dataList);
    }
}

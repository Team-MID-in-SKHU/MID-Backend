package com.skhu.mid_skhu.app.controller.event;

import com.skhu.mid_skhu.app.dto.event.responseDto.EventSearchResponseDto;
import com.skhu.mid_skhu.app.service.event.PartialEventSearchService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "행사/이벤트", description = "행사/이벤트를 조회하는 API 그룹")
@RequestMapping("/api/v1/event/display")
public class PartialEventSearchController {

    private final PartialEventSearchService partialEventSearchService;

    @GetMapping("/{title}")
    @Operation(
            summary = "이벤트 이름으로 조회",
            description = "이벤트 이름에 부분 일치하는 이벤트를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이벤트 이름으로 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<List<EventSearchResponseDto>>> findByPartialTitle(@PathVariable("title") String partialTitle) {
        ApiResponseTemplate<List<EventSearchResponseDto>> response = partialEventSearchService.partialSearchEvents(partialTitle);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

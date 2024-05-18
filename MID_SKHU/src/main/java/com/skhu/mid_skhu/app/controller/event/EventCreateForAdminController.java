package com.skhu.mid_skhu.app.controller.event;

import com.skhu.mid_skhu.app.dto.event.requestDto.EventCreateRequestDto;
import com.skhu.mid_skhu.app.dto.event.responseDto.EventCreateResponseDto;
import com.skhu.mid_skhu.app.service.event.EventCreateForAdminService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "행사/이벤트 ADMIN용", description = "행사/이벤트를 관리하는 ADMIN용 api그룹")
@RequestMapping("/api/v1/admin/event")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EventCreateForAdminController {

    private final EventCreateForAdminService eventCreateForAdminService;

    @PostMapping("/create")
    @Operation(
            summary = "행사/이벤트 생성",
            description = "행사/이벤트를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "행사/이벤트 생성 성공"),
                    @ApiResponse(responseCode = "403", description = "url문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<EventCreateResponseDto>> createEvent(@RequestBody EventCreateRequestDto requestDto, Principal principal) {

        ApiResponseTemplate<EventCreateResponseDto> data = eventCreateForAdminService.createEvent(requestDto, principal);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}

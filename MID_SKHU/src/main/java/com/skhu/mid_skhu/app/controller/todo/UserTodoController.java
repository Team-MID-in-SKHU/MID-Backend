package com.skhu.mid_skhu.app.controller.todo;

import com.skhu.mid_skhu.app.dto.todo.requestDto.CheckMonthTodoListRequestDto;
import com.skhu.mid_skhu.app.dto.user.responseDto.UserTodoListWrapperResponseDto;
import com.skhu.mid_skhu.app.service.todo.UserMonthTodoListCheckService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "todo", description = "사용자의 todolist를 관리하는 api 그룹")
@RequestMapping("/api/v1/todo")
public class UserTodoController {

    private final UserMonthTodoListCheckService userMonthTodoListCheckService;

    @GetMapping("/month")
    @Operation(
            summary = "나의 이번달 할 일 조회",
            description = "등록한 관심사에 맞는 본인의 이번달 일정을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "404", description = "사용자 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate<UserTodoListWrapperResponseDto>> getUserTodoList(Principal principal,
                                                                                               @RequestBody CheckMonthTodoListRequestDto requestDto) {
        ApiResponseTemplate<UserTodoListWrapperResponseDto> data = userMonthTodoListCheckService.checkMonthTodoList(principal, requestDto);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}

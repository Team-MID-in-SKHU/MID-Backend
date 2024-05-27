package com.skhu.mid_skhu.app.controller.user;

import com.skhu.mid_skhu.app.dto.todo.requestDto.CheckMonthTodoListRequestDto;
import com.skhu.mid_skhu.app.dto.user.responseDto.UserInfoResponseDto;
import com.skhu.mid_skhu.app.dto.user.responseDto.UserTodoListWrapperResponseDto;
import com.skhu.mid_skhu.app.service.user.UserInfoService;
import com.skhu.mid_skhu.app.service.todo.UserMonthTodoListCheckService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Tag(name = "사용자", description = "사용자 정보를 관리하는 api 그룹")
@RequestMapping("/api/v1/user")
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserMonthTodoListCheckService userMonthTodoListCheckService;

    @GetMapping("/info")
    @Operation(
            summary = "마이페이지 정보 조회",
            description = "현재 로그인된 사용자의 마이페이지 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "마이페이지 정보 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "404", description = "사용자 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate<UserInfoResponseDto>> getUserInfo(Principal principal) {
        ApiResponseTemplate<UserInfoResponseDto> data = userInfoService.getUserInfo(principal);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}

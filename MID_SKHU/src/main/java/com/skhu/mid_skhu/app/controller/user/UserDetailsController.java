package com.skhu.mid_skhu.app.controller.user;

import com.skhu.mid_skhu.app.dto.user.requestDto.UserDetailsUpdateRequestDto;
import com.skhu.mid_skhu.app.dto.user.responseDto.UserDetailsResponseDto;
import com.skhu.mid_skhu.app.service.user.UserDetailsService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Tag(name = "사용자 상세 정보", description = "사용자의 상세 정보를 관리하는 api 그룹")
@RequestMapping("/api/v1/user/details")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @GetMapping
    @Operation(
            summary = "사용자 상세 정보 조회",
            description = "현재 로그인된 사용자의 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 상세 정보 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "404", description = "사용자 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate<UserDetailsResponseDto>> getUserDetails(Principal principal) {
        ApiResponseTemplate<UserDetailsResponseDto> data = userDetailsService.getUserDetails(principal);

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PatchMapping("/edit")
    @Operation(
            summary = "사용자 상세 정보 수정",
            description = "현재 로그인된 사용자의 상세 정보를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 상세 정보 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
                    @ApiResponse(responseCode = "403", description = "권한 문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "404", description = "사용자 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate<UserDetailsResponseDto>> updateUserDetails(Principal principal, @RequestBody UserDetailsUpdateRequestDto requestDto) {
        ApiResponseTemplate<UserDetailsResponseDto> data = userDetailsService.updateUserDetails(principal, requestDto);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}

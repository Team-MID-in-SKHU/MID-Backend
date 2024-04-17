package com.skhu.mid_skhu.controller.auth;

import com.skhu.mid_skhu.dto.auth.requestDto.SignUpRequestDto;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.service.auth.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "로그인/회원가입", description = "로그인/회원가입을 담당하는 api그룹")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SignUpService signUpService;

    @PostMapping("/signUp")
    @Operation(
            summary = "회원가입",
            description = "사용자 정보를 받고 DB에 저장하고 refreshToken, accessToken을 발급해서 회원가입을 진행하고 사용자 정보와 함께 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "403", description = "url문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "409", description = "이미 존재하는 학번"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        ApiResponseTemplate apiResponseTemplate = signUpService.signUp(signUpRequestDto);
        return ResponseEntity.status(apiResponseTemplate.getStatus()).body(apiResponseTemplate);
    }
}

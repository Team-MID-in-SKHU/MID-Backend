package com.skhu.mid_skhu.controller.auth;

import com.skhu.mid_skhu.dto.auth.requestDto.SignUpRequestDto;
import com.skhu.mid_skhu.global.common.dto.ApiResponse;
import com.skhu.mid_skhu.service.auth.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "로그인/회원가입")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SignUpService signUpService;

    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "사용자 정보를 받고 DB에 저장하고 refreshToken, accessToken을 발급해서 회원가입을 진행하고 사용자 정보와 함께 반환합니다.")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        ApiResponse apiResponse = signUpService.signUp(signUpRequestDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}

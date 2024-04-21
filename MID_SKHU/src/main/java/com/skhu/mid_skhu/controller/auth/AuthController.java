package com.skhu.mid_skhu.controller.auth;

import com.skhu.mid_skhu.dto.auth.requestDto.LoginRequestDto;
import com.skhu.mid_skhu.dto.auth.requestDto.SignUpRequestDto;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.service.auth.LoginService;
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
    private final LoginService loginService;

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

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "사용자의 학번, 비밀번호를 받아와서 암호화되어서 저장된 Password와 매칭되는지를 검사하고 로그인을 통해서 갱신된 refreshToken, accessToken을 반환합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 암호 입력"),
                    @ApiResponse(responseCode = "404", description = "학번을 찾을수 없거나, 잘못된 값을 입력"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate> login(@RequestBody LoginRequestDto loginRequestDto) {
        ApiResponseTemplate data = loginService.login(loginRequestDto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}

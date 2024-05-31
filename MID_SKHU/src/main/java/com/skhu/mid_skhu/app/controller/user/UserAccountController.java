package com.skhu.mid_skhu.app.controller.user;

import com.skhu.mid_skhu.app.service.user.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "로그아웃/회원탈퇴", description = "로그아웃/회원탈퇴를 관리하는 api 그룹")
@RequestMapping("/api/v1/user/account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/logout")
    @Operation(
            summary = "사용자 로그아웃",
            description = "현재 로그인된 사용자를 로그아웃합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<String> logout() {
        userAccountService.logout();
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }
}

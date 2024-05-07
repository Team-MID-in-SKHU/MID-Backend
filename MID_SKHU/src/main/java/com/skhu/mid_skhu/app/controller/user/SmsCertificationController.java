package com.skhu.mid_skhu.app.controller.user;

import com.skhu.mid_skhu.app.dto.user.UserSmsDto;
import com.skhu.mid_skhu.app.service.user.UserCertificationService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.SuccessCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "sms인증", description = "sms 발송과 인증을 처리하는 api그룹")
@RequestMapping("api/v1/sms-certification")
public class SmsCertificationController {

    private final UserCertificationService userCertificationService;

    @PostMapping("/send")
    @Operation(
            summary = "sms 발송",
            description = "사용자에게 sms를 통해서 인증번호를 발송합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "sms 발송 성공"),
                    @ApiResponse(responseCode = "400", description = "sms 발송 실패"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate<String>> sendSms(@RequestBody UserSmsDto.SmsCertificationRequest requestDto) {

        try {
            userCertificationService.sendSms(requestDto);
            return ApiResponseTemplate.success(SuccessCode.SMS_CERT_MESSAGE_SUCCESS, SuccessCode.SMS_CERT_MESSAGE_SUCCESS.getMessage());
        } catch (CustomException e) {
            return ApiResponseTemplate.error(ErrorCode.SMS_CERTIFICATION_SEND_MISSING_EXCEPTION, ErrorCode.SMS_CERTIFICATION_SEND_MISSING_EXCEPTION.getMessage());
        }
    }

    @PostMapping("/confirm")
    @Operation(
            summary = "sms 인증",
            description = "사용자에게 받은 sms번호를 검증합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "sms 인증 성공"),
                    @ApiResponse(responseCode = "400", description = "sms 인증에 실패했습니다"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate<String>> smsVerification(@RequestBody UserSmsDto.SmsCertificationRequest requestDto) {
        try {
            userCertificationService.verifySms(requestDto);
            return ApiResponseTemplate.success(SuccessCode.SMS_VERIFY_SUCCESS, SuccessCode.SMS_VERIFY_SUCCESS.getMessage());
        } catch (CustomException e) {
            return ApiResponseTemplate.error(ErrorCode.SMS_CERTIFICATION_VERIFY_FAIL_EXCEPTION, ErrorCode.SMS_CERTIFICATION_VERIFY_FAIL_EXCEPTION.getMessage());
        }
    }
}

package com.skhu.mid_skhu.app.controller.interest;

import com.skhu.mid_skhu.app.dto.interest.requestDto.InterestRegisterRequestDto;
import com.skhu.mid_skhu.app.dto.interest.responseDto.InterestRegisterResponseDto;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.app.service.interest.InterestRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Tag(name = "관심사", description = "관심사를 관리하는 api그룹")
@RequestMapping("/api/v1/interest")
public class InterestController {

    private final InterestRegisterService registerService;

    @PostMapping("/register")
    @Operation(
            summary = "관심사 등록",
            description = "관심사를 List로 받고 받은 관심사 정보를 해당 사용자 정보에 저장합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "관심사 등록 성공"),
                    @ApiResponse(responseCode = "403", description = "url문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResponseTemplate<InterestRegisterResponseDto>> interestRegister(@RequestBody InterestRegisterRequestDto requestDto,
                                                                                             Principal principal) {
        ApiResponseTemplate<InterestRegisterResponseDto> data = registerService.interestRegister(requestDto, principal);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}

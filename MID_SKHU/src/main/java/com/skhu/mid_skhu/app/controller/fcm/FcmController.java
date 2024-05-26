package com.skhu.mid_skhu.app.controller.fcm;

import com.skhu.mid_skhu.app.dto.fcm.FcmRequestDto;
import com.skhu.mid_skhu.app.service.fcm.FirebaseCloudMessageService;
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

import java.io.IOException;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "fcm", description = "fcm을 통한 푸시알림을 처리하는 api그룹")
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/push")
    @Operation(
            summary = "fcm 푸시알림",
            description = "fcm을 통해서 푸시알림을 보냅니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "푸시알림 성공"),
                    @ApiResponse(responseCode = "400", description = "푸시알림 실패"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            }

    )
    public ResponseEntity pushMessage(@RequestBody FcmRequestDto fcmRequestDto) throws IOException {
        System.out.println(fcmRequestDto.getTargetToken() + " " + fcmRequestDto.getTitle() + " " + fcmRequestDto.getBody());

        firebaseCloudMessageService.sendMessageTo(
                fcmRequestDto.getTargetToken(),
                fcmRequestDto.getTitle(),
                fcmRequestDto.getBody());

        return ResponseEntity.ok().build();
    }
}

package com.skhu.mid_skhu.app.dto.fcm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FcmRequestDto { // 테스트용 requestDto

    private String targetToken;
    private String title;
    private String body;
}

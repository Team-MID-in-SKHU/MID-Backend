package com.skhu.mid_skhu.app.dto.auth.requestDto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String studentNo;
    private String password;
    private String fcmToken;
}

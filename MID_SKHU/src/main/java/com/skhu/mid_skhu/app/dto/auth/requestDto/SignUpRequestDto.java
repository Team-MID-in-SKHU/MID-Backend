package com.skhu.mid_skhu.app.dto.auth.requestDto;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String studentNo;
    private String password;
    private String name;
    private String phoneNumber;
    private String fcmToken;
    private String roleType;
}

package com.skhu.mid_skhu.app.dto.user;

import lombok.Getter;

public class UserSmsDto {

    @Getter
    public static class SmsCertificationRequest {

        private String phone;
        private String certificationNumber;
    }
}

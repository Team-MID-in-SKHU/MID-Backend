package com.skhu.mid_skhu.app.dto.user.requestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordSmsCertificationDto {

    private String phoneNumber;
}

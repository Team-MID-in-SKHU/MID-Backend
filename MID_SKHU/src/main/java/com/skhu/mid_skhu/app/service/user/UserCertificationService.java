package com.skhu.mid_skhu.app.service.user;

import com.skhu.mid_skhu.app.dto.user.UserSmsDto;

public interface UserCertificationService {

    void sendSms(UserSmsDto.SmsCertificationRequest requestDto);

    void verifySms(UserSmsDto.SmsCertificationRequest requestDto);

    boolean isVerify(UserSmsDto.SmsCertificationRequest requestDto);
}

package com.skhu.mid_skhu.app.service.user;

import com.skhu.mid_skhu.app.dto.user.UserSmsDto;
import com.skhu.mid_skhu.app.repository.SmsCertificationDao;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.global.util.SmsCertificationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCertificationServiceImpl implements UserCertificationService{

    private final SmsCertificationUtil smsUtil;
    private final SmsCertificationDao smsCertificationDao;

    public void sendSms(UserSmsDto.SmsCertificationRequest requestDto) {
        String to = requestDto.getPhone();
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        String certificationNumber = String.valueOf(randomNumber);
        smsUtil.sendSms(to, certificationNumber);
        smsCertificationDao.createSmsCertification(to, certificationNumber);
    }

    public void verifySms(UserSmsDto.SmsCertificationRequest requestDto) {
        if (isVerify(requestDto)) {
            throw new CustomException(ErrorCode.SMS_CERTIFICATION_MISMATCH_EXCEPTION,
                    ErrorCode.SMS_CERTIFICATION_MISMATCH_EXCEPTION.getMessage());
        }

        smsCertificationDao.removeSmsCertification(requestDto.getPhone());
    }

    public boolean isVerify(UserSmsDto.SmsCertificationRequest requestDto) {
        return !(smsCertificationDao.hasKey(requestDto.getPhone())) &&
                smsCertificationDao.getSmsCertification(requestDto.getPhone())
                        .equals(requestDto.getCertificationNumber());
    }
}

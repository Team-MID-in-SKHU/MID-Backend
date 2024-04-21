package com.skhu.mid_skhu.service.auth;

import com.skhu.mid_skhu.dto.auth.RefreshTokenParsingDto;
import com.skhu.mid_skhu.dto.auth.RenewAccessTokenDto;
import com.skhu.mid_skhu.entity.student.Student;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.global.jwt.TokenProvider;
import com.skhu.mid_skhu.repository.StudentRepository;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRenewService {

    private final StudentRepository studentRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public RenewAccessTokenDto renewAccessTokenDtoFromRefreshToken(String refreshToken) {

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_EXCEPTION,
                    ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
        }

        RefreshTokenParsingDto studentIdDto = getStudentInfoFromRefreshToken(refreshToken);

        Student student = studentRepository.findById(studentIdDto.getStudentId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        String renewAccessToken;

        try {
            renewAccessToken = tokenProvider.createAccessToken(student);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.TOKEN_CREATION_FAILED_EXCEPTION,
                    ErrorCode.TOKEN_CREATION_FAILED_EXCEPTION.getMessage());
        }

        return RenewAccessTokenDto.builder()
                .renewAccessToken(renewAccessToken)
                .build();
    }


    private RefreshTokenParsingDto getStudentInfoFromRefreshToken(String refreshToken) {
        Claims claims = tokenProvider.getClaimsFromToken(refreshToken);

        Long studentId = Long.parseLong(claims.getSubject());

        return RefreshTokenParsingDto.builder()
                .studentId(studentId)
                .build();
    }
}

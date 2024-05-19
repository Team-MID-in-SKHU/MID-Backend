package com.skhu.mid_skhu.app.service.auth;

import com.skhu.mid_skhu.app.dto.auth.RenewAccessTokenDto;
import com.skhu.mid_skhu.app.dto.auth.requestDto.LoginRequestDto;
import com.skhu.mid_skhu.app.dto.auth.responseDto.LoginResponseDto;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.entity.student.StudentRefreshToken;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.global.jwt.TokenProvider;
import com.skhu.mid_skhu.app.repository.StudentRefreshTokenRepository;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginService {

    private final StudentRepository studentRepository;
    private final StudentRefreshTokenRepository studentRefreshTokenRepository;

    private final TokenProvider tokenProvider;
    private final TokenRenewService tokenRenewService;
    private final PasswordEncoder passwordEncoder;

    // Student 엔티티에 studentNo 컬럼 옵션에 Unique 추가하기
    // 토큰을 검증하는 세세한 예외처리 로직을 만들고 사용하자

    @Transactional
    public ApiResponseTemplate<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        String studentNo = loginRequestDto.getStudentNo();

        Student student = studentRepository.findByStudentNo(studentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STUDENT_NUM_EXCEPTION,
                        ErrorCode.NOT_FOUND_STUDENT_NUM_EXCEPTION.getMessage() + "학번: " + studentNo));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), student.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH_EXCEPTION,
                    ErrorCode.PASSWORD_MISMATCH_EXCEPTION.getMessage());
        }

        String renewRefreshToken = tokenProvider.createRefreshToken(student);

        StudentRefreshToken refreshTokenEntity = studentRefreshTokenRepository.findByStudent_UserId(student.getUserId())
                .orElseGet(() -> {
                    StudentRefreshToken logoutStudentRenewRefreshToken = new StudentRefreshToken();
                    logoutStudentRenewRefreshToken.setStudent(student);
                    return studentRefreshTokenRepository.save(logoutStudentRenewRefreshToken);
                });

        refreshTokenEntity.setRefreshToken(renewRefreshToken);
        refreshTokenEntity.setStudent(student);

        studentRefreshTokenRepository.save(refreshTokenEntity);

        RenewAccessTokenDto renewAccessTokenDto = tokenRenewService.renewAccessTokenDtoFromRefreshToken(renewRefreshToken);
        String renewAccessToken = renewAccessTokenDto.getRenewAccessToken();

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .accessToken(renewAccessToken)
                .refreshToken(renewRefreshToken)
                .build();

        return ApiResponseTemplate.<LoginResponseDto>builder()
                .status(200)
                .success(true)
                .message("로그인 성공")
                .data(loginResponseDto)
                .build();
    }
}

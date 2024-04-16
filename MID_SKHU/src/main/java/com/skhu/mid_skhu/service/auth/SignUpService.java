package com.skhu.mid_skhu.service.auth;


import com.skhu.mid_skhu.dto.auth.requestDto.SignUpRequestDto;
import com.skhu.mid_skhu.dto.auth.responseDto.SignUpResponseDto;
import com.skhu.mid_skhu.entity.student.RoleType;
import com.skhu.mid_skhu.entity.student.Student;
import com.skhu.mid_skhu.entity.student.StudentRefreshToken;
import com.skhu.mid_skhu.global.common.dto.ApiResponse;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.global.jwt.TokenProvider;
import com.skhu.mid_skhu.repository.StudentRefreshTokenRepository;
import com.skhu.mid_skhu.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpService {

    private final StudentRepository studentRepository;
    private final StudentRefreshTokenRepository studentRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public ApiResponse<SignUpResponseDto> signUp(SignUpRequestDto signUpRequestDto) {

        String encodePassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        signUpRequestDto.setPassword(encodePassword);

        if (studentRepository.existsByStudentNo(signUpRequestDto.getStudentNo())) { // 학번 중복검사
            throw new CustomException(ErrorCode.ALREADY_EXIST_STUDENT_EXCEPTION,
                    ErrorCode.ALREADY_EXIST_STUDENT_EXCEPTION.getMessage());
        }

        RoleType roleType = RoleType.getRoleTypeOfString(signUpRequestDto.getRoleType());

        Student student = studentRepository.save(Student.builder()
                .studentNo(signUpRequestDto.getStudentNo())
                .password(signUpRequestDto.getPassword())
                .name(signUpRequestDto.getName())
                .phoneNumber(signUpRequestDto.getPhoneNumber())
                .roleType(roleType)
                .build()
        );

        String accessToken = tokenProvider.createAccessToken(student);

        String refreshToken = tokenProvider.createRefreshToken(student);

        StudentRefreshToken userRefreshToken = new StudentRefreshToken();
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setStudent(student);

        studentRefreshTokenRepository.deleteByStudent(student);

        studentRefreshTokenRepository.save(userRefreshToken);

        SignUpResponseDto responseDto = SignUpResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResponse.<SignUpResponseDto>builder()
                .status(201)
                .success(true)
                .message("회원가입 성공")
                .data(responseDto)
                .build();
    }
}

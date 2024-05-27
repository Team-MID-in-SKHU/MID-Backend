package com.skhu.mid_skhu.app.service.auth;


import com.skhu.mid_skhu.app.dto.auth.requestDto.SignUpRequestDto;
import com.skhu.mid_skhu.app.dto.auth.requestDto.StudentNoDuplicateInspectionRequestDto;
import com.skhu.mid_skhu.app.dto.auth.responseDto.AuthResponseDto;
import com.skhu.mid_skhu.app.dto.auth.responseDto.StudentNoDuplicateInspectionResponseDto;
import com.skhu.mid_skhu.app.entity.student.RoleType;
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
public class SignUpService {

    private final StudentRepository studentRepository;
    private final StudentRefreshTokenRepository studentRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public ApiResponseTemplate<AuthResponseDto> signUp(SignUpRequestDto signUpRequestDto) {

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
                .fcmToken(signUpRequestDto.getFcmToken())
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

        AuthResponseDto responseDto = AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResponseTemplate.<AuthResponseDto>builder()
                .status(201)
                .success(true)
                .message("회원가입 성공")
                .data(responseDto)
                .build();
    }

    @Transactional(readOnly = true)
    public ApiResponseTemplate<StudentNoDuplicateInspectionResponseDto> studentNoDuplicateInspection
            (StudentNoDuplicateInspectionRequestDto studentNoDuplicateInspectionRequestDto) {

        Boolean isDuplicate = studentRepository.existsByStudentNo(studentNoDuplicateInspectionRequestDto.getStudentNo());

        StudentNoDuplicateInspectionResponseDto responseDto = StudentNoDuplicateInspectionResponseDto.builder()
                .studentNo(isDuplicate)
                .build();

        return ApiResponseTemplate.<StudentNoDuplicateInspectionResponseDto>builder()
                .status(200)
                .success(true)
                .message("중복검사 성공")
                .data(responseDto)
                .build();
    }
}

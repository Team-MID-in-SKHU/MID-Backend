package com.skhu.mid_skhu.app.service.user;

import com.skhu.mid_skhu.app.dto.user.requestDto.UserDetailsUpdateRequestDto;
import com.skhu.mid_skhu.app.dto.user.responseDto.UserDetailsResponseDto;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsService {

    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<UserDetailsResponseDto> getUserDetails(Principal principal) {
        Long studentId = Long.parseLong(principal.getName());

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "학생 : " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        UserDetailsResponseDto responseDto = UserDetailsResponseDto.builder()
                .studentNo(student.getStudentNo())
                .name(student.getName())
                .phoneNumber(student.getPhoneNumber())
                .department(student.getDepartment())
                .roleType(student.getRoleType())
                .category(student.getCategory())
                .build();

        return ApiResponseTemplate.<UserDetailsResponseDto>builder()
                .status(200)
                .success(true)
                .message("유저 상세 정보 조회 성공")
                .data(responseDto)
                .build();
    }

    @Transactional
    public ApiResponseTemplate<UserDetailsResponseDto> updateUserDetails(Principal principal, UserDetailsUpdateRequestDto requestDto) {
        Long studentId = Long.parseLong(principal.getName());

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "학생 : " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        student.updateDetails(requestDto.getName(), requestDto.getPhoneNumber());

        UserDetailsResponseDto responseDto = UserDetailsResponseDto.builder()
                .studentNo(student.getStudentNo())
                .name(student.getName())
                .phoneNumber(student.getPhoneNumber())
                .roleType(student.getRoleType())
                .category(student.getCategory())
                .build();

        return ApiResponseTemplate.<UserDetailsResponseDto>builder()
                .status(200)
                .success(true)
                .message("유저 상세 정보 수정 성공")
                .data(responseDto)
                .build();
    }
}

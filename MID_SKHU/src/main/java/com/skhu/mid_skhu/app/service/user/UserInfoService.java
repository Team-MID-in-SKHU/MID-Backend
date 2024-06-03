package com.skhu.mid_skhu.app.service.user;

import com.skhu.mid_skhu.app.dto.user.responseDto.UserInfoResponseDto;
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
public class UserInfoService {

    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<UserInfoResponseDto> getUserInfo(Principal principal) {
        Long studentId = Long.parseLong(principal.getName());

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "학생 : " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        UserInfoResponseDto responseDto = UserInfoResponseDto.builder()
                .studentNo(student.getStudentNo())
                .name(student.getName())
                .department(student.getDepartment())
                .roleType(student.getRoleType())
                .build();

        return ApiResponseTemplate.<UserInfoResponseDto>builder()
                .status(200)
                .success(true)
                .message("마이페이지 정보 조회 성공")
                .data(responseDto)
                .build();
    }
}

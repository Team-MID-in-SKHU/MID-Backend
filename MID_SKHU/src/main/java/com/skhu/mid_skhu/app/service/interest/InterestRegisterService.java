package com.skhu.mid_skhu.app.service.interest;

import com.skhu.mid_skhu.app.dto.interest.requestDto.InterestRegisterRequestDto;
import com.skhu.mid_skhu.app.dto.interest.responseDto.InterestRegisterResponseDto;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestRegisterService {

    private final StudentRepository studentRepository;

    @Transactional
    public ApiResponseTemplate<InterestRegisterResponseDto> interestRegister(InterestRegisterRequestDto requestDto, Principal principal) {

        Long studentId = Long.parseLong(principal.getName());

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "학생 : " +ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        List<InterestCategory> categories = requestDto.getInterestCategoryList().stream()
                .map(InterestCategory::convertToCategory)
                .collect(Collectors.toList());

        student.getCategory().clear();
        student.getCategory().addAll(categories);
        studentRepository.save(student);

        InterestRegisterResponseDto responseDto = InterestRegisterResponseDto.builder()
                .interestCategoryList(categories.stream()
                        .map(InterestCategory::getDisplayName)
                        .collect(Collectors.toList()))
                .build();

        return ApiResponseTemplate.<InterestRegisterResponseDto>builder()
                .status(201)
                .success(true)
                .message("관심사 등록 성공")
                .data(responseDto)
                .build();
    }
}

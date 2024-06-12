package com.skhu.mid_skhu.app.service.interest;

import com.skhu.mid_skhu.app.dto.interest.requestDto.InterestUpdateRequestDto;
import com.skhu.mid_skhu.app.dto.interest.responseDto.InterestUpdateResponseDto;
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
public class InterestUpdateService {

    private final StudentRepository studentRepository;

    @Transactional
    public ApiResponseTemplate<InterestUpdateResponseDto> interestUpdate(InterestUpdateRequestDto requestDto, Principal principal) {

        Long studentId = Long.parseLong(principal.getName());

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "학생 : " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        List<InterestCategory> newCategories = requestDto.getInterestCategoryList().stream()
                .map(InterestCategory::convertToCategory)
                .collect(Collectors.toList());

        List<InterestCategory> existingCategories = student.getCategory();

        for (InterestCategory newCategory : newCategories) {
            if (!existingCategories.contains(newCategory)) {
                existingCategories.add(newCategory);
            }
        }

        existingCategories.retainAll(newCategories);

        studentRepository.save(student);

        InterestUpdateResponseDto responseDto = InterestUpdateResponseDto.builder()
                .interestCategoryList(existingCategories.stream()
                        .map(InterestCategory::getDisplayName)
                        .collect(Collectors.toList()))
                .build();

        return ApiResponseTemplate.<InterestUpdateResponseDto>builder()
                .status(200)
                .success(true)
                .message("관심사 수정 성공")
                .data(responseDto)
                .build();
    }
}

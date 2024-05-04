package com.skhu.mid_skhu.app.service.interest;

import com.skhu.mid_skhu.app.dto.interest.requestDto.InterestRegisterRequestDto;
import com.skhu.mid_skhu.app.dto.interest.responseDto.InterestRegisterResponseDto;
import com.skhu.mid_skhu.app.entity.interest.Interest;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.app.repository.InterestRepository;
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

    private final InterestRepository interestRepository;
    private final StudentRepository studentRepository;

    // feat#13 으로 커밋하기
    // 이슈파고 -> docs 브랜치 파고 -> api문서 수정하고 커밋하기
    @Transactional
    public ApiResponseTemplate<InterestRegisterResponseDto> interestRegister(InterestRegisterRequestDto requestDto, Principal principal) {
        // 보안컨텍스트에 null이 담겨진 경우(토큰이 잘못된 경우)에 대한 전역 예외처리 해놓기

        Long studentId = Long.parseLong(principal.getName());

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "학생 : " +ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        List<Interest> interests = requestDto.getInterestCategoryList().stream()
                .map(category -> {
                    Interest interest = Interest.builder()
                            .category(InterestCategory.convertToCategory(category))
                            .student(student)
                            .build();
                    return interestRepository.save(interest);
                })
                .collect(Collectors.toList());

        student.getInterests().addAll(interests);

        InterestRegisterResponseDto responseDto = InterestRegisterResponseDto.builder()
                .interestCategoryList(interests.stream()
                        .map(interest -> interest.getCategory().name())
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

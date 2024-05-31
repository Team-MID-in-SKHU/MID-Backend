package com.skhu.mid_skhu.app.service.todo;

import com.skhu.mid_skhu.app.dto.user.responseDto.UserTodoListResponseDto;
import com.skhu.mid_skhu.app.dto.user.responseDto.UserTodoListWrapperResponseDto;
import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.student.Student;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.app.repository.StudentRepository;
import com.skhu.mid_skhu.app.service.fcm.alarmInterface.UserTodayTodoListCheckService;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTodayTodoListCheckServiceImpl implements UserTodayTodoListCheckService {

    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public ApiResponseTemplate<UserTodoListWrapperResponseDto> checkTodayTodoList(Principal principal) {
        LocalDateTime todayStartAt = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime todayEndAt = todayStartAt.plusDays(1).minusNanos(1);

        Long userId = Long.parseLong(principal.getName());
        Student student = getStudentById(userId);

        List<Event> eventList = eventRepository.findTodayEventsByCategories(student.getCategory(), todayStartAt, todayEndAt);

        return createApiResponse(eventList);
    }

    private Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage() + "ID: " + id));
    }

    private ApiResponseTemplate<UserTodoListWrapperResponseDto> createApiResponse(List<Event> eventList) {
        if (eventList.isEmpty()) {
            return ApiResponseTemplate.<UserTodoListWrapperResponseDto>builder()
                    .status(200)
                    .success(true)
                    .message("조회에는 성공했으나 등록한 관심사에 맞는 이벤트 일정이 없습니다.")
                    .data(UserTodoListWrapperResponseDto.builder().responseDto(List.of()).build())
                    .build();
        }

        List<UserTodoListResponseDto> responseDtoList = eventList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        UserTodoListWrapperResponseDto wrapperResponseDto = UserTodoListWrapperResponseDto.builder()
                .responseDto(responseDtoList)
                .build();

        return ApiResponseTemplate.<UserTodoListWrapperResponseDto>builder()
                .status(200)
                .success(true)
                .message("조회 성공")
                .data(wrapperResponseDto)
                .build();
    }


    private UserTodoListResponseDto convertToDto(Event event) {
        return UserTodoListResponseDto.builder()
                .eventId(event.getId())
                .eventTitle(event.getTitle())
                .eventDescription(event.getDescription())
                .eventLocation(event.getEventLocation())
                .startAt(event.getStartAt())
                .endAt(event.getEndAt())
                .build();
    }
}

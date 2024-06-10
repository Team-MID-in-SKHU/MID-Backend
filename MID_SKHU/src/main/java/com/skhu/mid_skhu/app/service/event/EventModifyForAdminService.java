package com.skhu.mid_skhu.app.service.event;

import com.skhu.mid_skhu.app.dto.event.requestDto.EventUpdateRequestDto;
import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventModifyForAdminService {

    private final EventRepository eventRepository;

    public ApiResponseTemplate<String> updateEventDetail(Principal principal, EventUpdateRequestDto requestDto) {

        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "게시글: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Long userId = findUserId(principal);

        validateEventWriterUser(event.getUserId(), userId);

        event.updateEventDetails(requestDto);

        return ApiResponseTemplate.<String>builder()
                .status(205)
                .success(true)
                .message("수정에 성공했습니다")
                .data("")
                .build();
    }

    public ApiResponseTemplate<String> deleteEvent(Principal principal, Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Long userId = findUserId(principal);

        validateEventWriterUser(event.getUserId(), userId);

        eventRepository.deleteById(eventId);

        return ApiResponseTemplate.<String>builder()
                .status(204)
                .success(true)
                .message("삭제에 성공했습니다")
                .data("")
                .build();
    }

    private Long findUserId(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        return userId;
    }

    public void validateEventWriterUser(Long writerId, Long userId) {
        if (!writerId.equals(userId)) {
            throw new CustomException(ErrorCode.ONLY_OWN_EVENT_MODIFY_EXCEPTION,
                    ErrorCode.ONLY_OWN_EVENT_MODIFY_EXCEPTION.getMessage());
        }
    }
}

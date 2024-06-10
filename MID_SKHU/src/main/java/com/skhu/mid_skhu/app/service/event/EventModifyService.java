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
public class EventModifyService {

    private final EventRepository eventRepository;

    public ApiResponseTemplate<String> updateEventDetail(Principal principal, EventUpdateRequestDto requestDto) {

        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "게시글: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Long writerId = Long.parseLong(principal.getName());

        if (!event.getUserId().equals(writerId)) {
            throw new CustomException(ErrorCode.ONLY_OWN_EVENT_MODIFY_EXCEPTION,
                    ErrorCode.ONLY_OWN_EVENT_MODIFY_EXCEPTION.getMessage());
        }

        event.updateEventDetails(requestDto);

        return ApiResponseTemplate.<String>builder()
                .status(205)
                .success(true)
                .message("수정에 성공했습니다")
                .data("")
                .build();
    }
}

package com.skhu.mid_skhu.app.service.event;

import com.skhu.mid_skhu.app.dto.event.responseDto.EventSearchResponseDto;
import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventDetailService {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<EventSearchResponseDto> getEventDetail(Long eventId, Principal principal) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "게시글:" + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        List<String> images = event.getImageUrls();

        EventSearchResponseDto responseDto = EventSearchResponseDto.builder()
                .eventId(event.getId())
                .eventTitle(event.getTitle())
                .eventDescription(event.getDescription())
                .eventLocation(event.getEventLocation())
                .startAt(event.getStartAt())
                .endAt(event.getEndAt())
                .imageUrls(images)
                .category(event.getCategories().stream()
                        .map(InterestCategory::getDisplayName)
                        .collect(Collectors.toList()))
                .build();

        return ApiResponseTemplate.<EventSearchResponseDto>builder()
                .status(200)
                .success(true)
                .message("이벤트 상세 조회 성공")
                .data(responseDto)
                .build();
    }
}

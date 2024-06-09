package com.skhu.mid_skhu.app.service.event;

import com.skhu.mid_skhu.app.dto.event.responseDto.EventSearchResponseDto;
import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventDisplayService {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<EventSearchResponseDto>> displayOneOngoingAndOneEndingEvent() {
        LocalDateTime currentTime = LocalDateTime.now();

        List<Event> ongoingEvents = eventRepository.findOngoingEvents(currentTime);
        if (ongoingEvents.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_EVENT_DATA_EXCEPTION,
                    "신청중인 이벤트가 없습니다.");
        }
        Event ongoingEvent = ongoingEvents.get(0);

        List<Event> nearestEndEvents = eventRepository.findEventsWithNearestEndAt(currentTime);
        if (nearestEndEvents.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_EVENT_DATA_EXCEPTION,
                    "마감임박 이벤트가 없습니다.");
        }
        Event nearestEndEvent = nearestEndEvents.get(0);

        EventSearchResponseDto ongoingEventDto = EventSearchResponseDto.builder()
                .eventTitle(ongoingEvent.getTitle())
                .eventDescription(ongoingEvent.getDescription())
                .eventLocation(ongoingEvent.getEventLocation())
                .startAt(ongoingEvent.getStartAt())
                .endAt(ongoingEvent.getEndAt())
                .category(ongoingEvent.getCategories().stream()
                        .map(category -> category.getCode())
                        .collect(Collectors.toList()))
                .build();

        EventSearchResponseDto nearestEndEventDto = EventSearchResponseDto.builder()
                .eventTitle(nearestEndEvent.getTitle())
                .eventDescription(nearestEndEvent.getDescription())
                .eventLocation(nearestEndEvent.getEventLocation())
                .startAt(nearestEndEvent.getStartAt())
                .endAt(nearestEndEvent.getEndAt())
                .category(nearestEndEvent.getCategories().stream()
                        .map(category -> category.getCode())
                        .collect(Collectors.toList()))
                .build();

        return ApiResponseTemplate.<List<EventSearchResponseDto>>builder()
                .status(200)
                .success(true)
                .message("신청중/마감임박 이벤트 조회 성공")
                .data(List.of(ongoingEventDto, nearestEndEventDto))
                .build();
    }
}

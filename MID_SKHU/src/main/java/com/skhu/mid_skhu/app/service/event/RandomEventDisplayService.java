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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomEventDisplayService {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<EventSearchResponseDto>> displayRandomEvents() {
        List<Event> ongoingEvents = eventRepository.findOngoingEvents(LocalDateTime.now());
        if (ongoingEvents.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_EVENT_DATA_EXCEPTION,
                    ErrorCode.NOT_FOUND_EVENT_DATA_EXCEPTION.getMessage());
        }

        List<EventSearchResponseDto> responseDtoList = new ArrayList<>();
        Random random = new Random();
        int eventCount = ongoingEvents.size();
        int maxEventsToDisplay = Math.min(eventCount, 2);
        Set<Integer> selectedIndexes = new HashSet<>();
        while (selectedIndexes.size() < maxEventsToDisplay) {
            int randomIndex = random.nextInt(eventCount);
            if (!selectedIndexes.contains(randomIndex)) {
                selectedIndexes.add(randomIndex);
                Event randomEvent = ongoingEvents.get(randomIndex);
                EventSearchResponseDto responseDto = EventSearchResponseDto.builder()
                        .eventTitle(randomEvent.getTitle())
                        .eventDescription(randomEvent.getDescription())
                        .eventLocation(randomEvent.getEventLocation())
                        .startAt(randomEvent.getStartAt())
                        .endAt(randomEvent.getEndAt())
                        .category(randomEvent.getCategories().stream()
                                .map(category -> category.getCode())
                                .collect(Collectors.toList()))
                        .build();
                responseDtoList.add(responseDto);
            }
        }

        return ApiResponseTemplate.<List<EventSearchResponseDto>>builder()
                .status(200)
                .success(true)
                .message("랜덤 이벤트 조회 성공")
                .data(responseDtoList)
                .build();
    }
}

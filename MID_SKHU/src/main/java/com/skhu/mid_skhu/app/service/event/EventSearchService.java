package com.skhu.mid_skhu.app.service.event;

import com.skhu.mid_skhu.app.dto.event.requestDto.EventSearchRequestDto;
import com.skhu.mid_skhu.app.dto.event.responseDto.EventSearchResponseDto;
import com.skhu.mid_skhu.app.entity.event.Event;
import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.repository.EventRepository;
import com.skhu.mid_skhu.app.repository.specification.EventSpecification;
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
public class EventSearchService {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<EventSearchResponseDto>> searchEvents(Principal principal,
                                                                          EventSearchRequestDto eventSearchRequestDto) {

        List<InterestCategory> categories = InterestCategory.convertToCategoryList(eventSearchRequestDto.getCategoryList());

        List<Event> events = eventRepository.findAll(EventSpecification.withDynamicQuery(
                eventSearchRequestDto.getTitle(),
                eventSearchRequestDto.getStartAt(),
                eventSearchRequestDto.getEndAt(),
                categories
        ));

        List<EventSearchResponseDto> responseDtoList = events.stream()
                .map(event -> EventSearchResponseDto.builder()
                        .eventId(event.getId())
                        .eventTitle(event.getTitle())
                        .eventDescription(event.getDescription())
                        .eventLocation(event.getEventLocation())
                        .startAt(event.getStartAt())
                        .endAt(event.getEndAt())
                        .category(event.getCategories().stream()
                                .map(InterestCategory::getDisplayName)
                                .collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());

        if (events.isEmpty()) {
            return ApiResponseTemplate.<List<EventSearchResponseDto>>builder()
                    .status(200)
                    .success(true)
                    .message("해당 검색결과에 부합하는 이벤트가 존재하지 않습니다")
                    .data(responseDtoList)
                    .build();
        }

        return ApiResponseTemplate.<List<EventSearchResponseDto>>builder()
                .status(200)
                .success(true)
                .message("조회 성공")
                .data(responseDtoList)
                .build();
    }
}
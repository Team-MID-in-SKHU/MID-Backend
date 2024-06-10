package com.skhu.mid_skhu.app.dto.event.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventUpdateRequestDto {

    private Long eventId;
    private String title;
    private String description;
    private String eventLocation;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<String> interestCategories;
}

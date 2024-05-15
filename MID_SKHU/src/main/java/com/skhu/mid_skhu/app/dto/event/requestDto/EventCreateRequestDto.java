package com.skhu.mid_skhu.app.dto.event.requestDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventCreateRequestDto {

    private String title;
    private String description;
    private String eventLocation;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<String> interestCategoryList;
}

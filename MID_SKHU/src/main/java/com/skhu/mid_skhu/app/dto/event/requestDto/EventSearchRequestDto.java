package com.skhu.mid_skhu.app.dto.event.requestDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventSearchRequestDto {

    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<String> categoryList;
}

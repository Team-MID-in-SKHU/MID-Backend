package com.skhu.mid_skhu.app.dto.event.responseDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class EventCreateResponseDto {


    private String title;
    private String description;
    private String eventLocation;
    private List<String> imageUrls;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<String> interestCategoryList;
}

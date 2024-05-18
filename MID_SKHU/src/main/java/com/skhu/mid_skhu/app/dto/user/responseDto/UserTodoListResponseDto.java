package com.skhu.mid_skhu.app.dto.user.responseDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserTodoListResponseDto {

    private Long eventId;
    private String eventTitle;
    private String eventDescription;
    private String eventLocation;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}

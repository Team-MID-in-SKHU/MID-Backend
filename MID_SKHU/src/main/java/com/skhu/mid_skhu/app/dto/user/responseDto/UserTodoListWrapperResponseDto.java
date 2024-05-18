package com.skhu.mid_skhu.app.dto.user.responseDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTodoListWrapperResponseDto {

    private List<UserTodoListResponseDto> responseDto;
}

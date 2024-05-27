package com.skhu.mid_skhu.app.dto.todo.requestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckMonthTodoListRequestDto {

    private Integer year;
    private Integer month;
}

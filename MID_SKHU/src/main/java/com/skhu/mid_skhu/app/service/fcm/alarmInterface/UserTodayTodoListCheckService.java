package com.skhu.mid_skhu.app.service.fcm.alarmInterface;

import com.skhu.mid_skhu.app.dto.user.responseDto.UserTodoListWrapperResponseDto;
import com.skhu.mid_skhu.global.common.dto.ApiResponseTemplate;

import java.security.Principal;

public interface UserTodayTodoListCheckService {
    ApiResponseTemplate<UserTodoListWrapperResponseDto> checkTodayTodoList(Principal principal);
}

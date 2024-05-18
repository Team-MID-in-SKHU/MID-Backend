package com.skhu.mid_skhu.app.dto.user.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class UserTodoListRequestDto {

    private List<String> userInterestCategoryList;
}

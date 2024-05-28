package com.skhu.mid_skhu.app.dto.user.responseDto;

import com.skhu.mid_skhu.app.entity.student.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {

    private String studentNo;
    private String name;
    private RoleType roleType;
}

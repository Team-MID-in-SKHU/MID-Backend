package com.skhu.mid_skhu.app.dto.user.responseDto;

import com.skhu.mid_skhu.app.entity.student.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoResponseDto {

    private Long studentNo;
    private String name;
    private RoleType roleType;
}

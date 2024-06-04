package com.skhu.mid_skhu.app.dto.user.responseDto;

import com.skhu.mid_skhu.app.entity.interest.InterestCategory;
import com.skhu.mid_skhu.app.entity.student.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDetailsResponseDto {

    private String studentNo;
    private String name;
    private String phoneNumber;
    private String department;
    private RoleType roleType;
    private List<InterestCategory> category;
}

package com.skhu.mid_skhu.dto.auth.requestDto;

import com.skhu.mid_skhu.entity.student.RoleType;
import lombok.Data;

import java.util.List;

@Data
public class SignUpRequestDto {

    private String studentNo;
    private String password;
    private String name;
    private String phoneNumber;
    private String roleType;
}

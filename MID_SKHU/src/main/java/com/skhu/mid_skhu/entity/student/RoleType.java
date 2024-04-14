package com.skhu.mid_skhu.entity.student;


import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ROLE_STUDENT("STUDENT", "일반 학생"),
    ROLE_ADMIN("ADMIN", "관리자");

    private final String code;
    private final String displayName;

    public static RoleType getRoleTypeOfString(String roleType) {
        for (RoleType type : RoleType.values()) {
            if (type.code.equals(roleType)) {
                return type;
            }
        }

        throw new CustomException(ErrorCode.INVALID_ROLE_TYPE_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
    }
}
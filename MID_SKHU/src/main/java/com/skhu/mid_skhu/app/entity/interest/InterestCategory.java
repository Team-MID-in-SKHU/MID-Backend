package com.skhu.mid_skhu.app.entity.interest;

import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum InterestCategory {

    ACADEMIC_SCHEDULE("ACADEMIC_SCHEDULE", "학사일정"),
    STUDENT_COUNCIL_SCHEDULE("STUDENT_COUNCIL_SCHEDULE", "학생회일정"),
    CLUB_SCHEDULE("CLUB_SCHEDULE", "동아리 일정"),
    SMALL_GROUP("SMALL_GROUP", "소모임"),
    PRE_CLUB("PRE_CLUB", "준동아리"),
    EXTERNAL_CLUB("EXTERNAL_CLUB", "대외동아리");

    private final String code;
    private final String displayName;

    public static InterestCategory convertToCategory(String categoryStr) {
        for (InterestCategory category : InterestCategory.values()) {
            if (category.code.equals(categoryStr)) {
                return category;
            }
        }

        throw new CustomException(ErrorCode.INVALID_DISPLAY_NAME_EXCEPTION,
                ErrorCode.INVALID_DISPLAY_NAME_EXCEPTION.getMessage());
    }
}

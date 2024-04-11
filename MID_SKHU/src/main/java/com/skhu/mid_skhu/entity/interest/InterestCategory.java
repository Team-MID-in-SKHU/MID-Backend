package com.skhu.mid_skhu.entity.interest;

import lombok.Getter;

@Getter
public enum InterestCategory {

    ACADEMIC_SCHEDULE("학사일정"),
    STUDENT_COUNCIL_SCHEDULE("학생회일정"),
    CLUB_SCHEDULE("동아리 일정"),
    SMALL_GROUP("소모임"),
    PRE_CLUB("준동아리"),
    EXTERNAL_CLUB("대외동아리");

    private final String displayName;

    InterestCategory(String displayName) {
        this.displayName = displayName;
    }
}

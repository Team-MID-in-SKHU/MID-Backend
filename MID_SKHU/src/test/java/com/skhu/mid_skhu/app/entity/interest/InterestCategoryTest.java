package com.skhu.mid_skhu.app.entity.interest;

import org.junit.jupiter.api.Test;

import static com.skhu.mid_skhu.app.entity.interest.InterestCategory.ACADEMIC_SCHEDULE;
import static org.junit.jupiter.api.Assertions.*;

class InterestCategoryTest {

        @Test
        void convertToCategory() {
            assertEquals(ACADEMIC_SCHEDULE, InterestCategory.convertToCategory("ACADEMIC_SCHEDULE"));
        }

}
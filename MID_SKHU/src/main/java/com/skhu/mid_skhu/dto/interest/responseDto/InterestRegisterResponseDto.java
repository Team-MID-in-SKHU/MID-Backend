package com.skhu.mid_skhu.dto.interest.responseDto;

import com.skhu.mid_skhu.entity.interest.InterestCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InterestRegisterResponseDto {

    private List<String> interestCategoryList;
}

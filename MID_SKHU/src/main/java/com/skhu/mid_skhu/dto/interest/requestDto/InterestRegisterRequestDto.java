package com.skhu.mid_skhu.dto.interest.requestDto;

import com.skhu.mid_skhu.entity.interest.InterestCategory;
import lombok.Data;

import java.util.List;

@Data
public class InterestRegisterRequestDto {

    private List<String> interestCategoryList;
}

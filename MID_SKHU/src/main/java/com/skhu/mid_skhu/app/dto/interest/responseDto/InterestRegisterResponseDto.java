package com.skhu.mid_skhu.app.dto.interest.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InterestRegisterResponseDto {

    private List<String> interestCategoryList;
}

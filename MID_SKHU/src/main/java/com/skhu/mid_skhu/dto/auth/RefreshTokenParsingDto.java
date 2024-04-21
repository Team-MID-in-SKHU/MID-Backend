package com.skhu.mid_skhu.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class RefreshTokenParsingDto {

    private Long studentId;
}

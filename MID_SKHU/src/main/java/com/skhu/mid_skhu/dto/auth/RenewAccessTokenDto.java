package com.skhu.mid_skhu.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RenewAccessTokenDto {

    private String renewAccessToken;
}

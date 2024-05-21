package com.skhu.mid_skhu.app.dto.user.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserDetailsUpdateRequestDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phoneNumber;
}

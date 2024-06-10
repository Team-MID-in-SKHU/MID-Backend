package com.skhu.mid_skhu.global.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GetS3Res {

    private String imageUrl;
    private String fileName;
}

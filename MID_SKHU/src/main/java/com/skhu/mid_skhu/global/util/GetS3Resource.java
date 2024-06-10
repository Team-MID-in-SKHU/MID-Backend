package com.skhu.mid_skhu.global.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GetS3Resource {

    private String imageUrl;
    private String fileName;
}

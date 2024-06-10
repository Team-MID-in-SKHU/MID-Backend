package com.skhu.mid_skhu.app.dto.event.requestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventCreateOfImageFileRequestDto {

    List<MultipartFile> images;
}

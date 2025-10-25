package com.skhu.mid_skhu.app.dto.event.responseDto;

public record S3UploadResponse(
        String bucket,
        String key,
        String url,
        Long size,
        String description
) {
}

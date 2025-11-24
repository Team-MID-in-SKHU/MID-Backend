package com.skhu.mid_skhu.app.dto.event.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record PreSignedUploadResponse(

        @Schema(description = "버킷 이름")
        String bucket,

        @Schema(description = "이벤트 업로드 경로 prefix를 포함 전체 s3 key (예: events/{category}/...")
        String key,

        @Schema(description = "클라이언트가 PUT 요청을 보내야하는 PreSigned URL")
        String url,

        @Schema(description = "URL 만료시간")
        Instant expireAt,

        @Schema(description = "업로드에 사용해야 하는 http 메서드 - PUT")
        String method,

        @Schema(description = "클라이언트가 업로드 시 반드시 이 Content-Type으로 요청해야 함 (다를시에 403")
        String contentType
) {
}

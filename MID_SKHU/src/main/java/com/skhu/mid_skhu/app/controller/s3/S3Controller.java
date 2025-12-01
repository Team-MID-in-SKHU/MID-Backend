package com.skhu.mid_skhu.app.controller.s3;

import com.skhu.mid_skhu.app.dto.event.responseDto.PreSignedUploadResponse;
import com.skhu.mid_skhu.app.dto.event.responseDto.S3UploadResponse;
import com.skhu.mid_skhu.app.service.event.S3ImageFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "s3", description = "이미지를 관리하는 api그룹")
@RequestMapping("/api/v1/s3")
public class S3Controller {

    private final S3ImageFileService s3ImageFileService;

    @PostMapping("/upload")
    @Operation(
            summary = "이미지 업로드",
            description = "S3 버킷에 이미지를 업로드합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이미지 업로드 성공"),
                    @ApiResponse(responseCode = "400", description = "이미지 업로드 실패"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            }
    )
    public ResponseEntity<S3UploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "directory", required = false, defaultValue = "events/test") String directory
    ) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(s3ImageFileService.uploadMultipartImage(file, directory));
    }

    @PostMapping("/presign")
    public ResponseEntity<PreSignedUploadResponse> presignUpload(
            @RequestParam(value = "directory", defaultValue = "events/test") String directory,
            @RequestParam("filename") String fileName,
            @RequestParam("contentType") String contentType
    ) {
        return ResponseEntity.ok(
                s3ImageFileService.createPreSignedUploadUrl(directory, fileName, contentType)
        );
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "이미지 삭제",
            description = "S3 버킷에서 이미지를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "204", description = "이미지 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "이미지 삭제 실패"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            }
    )
    public ResponseEntity<Void> deleteFile(@RequestParam("key") String key) {
        if (key == null || key.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        s3ImageFileService.deleteFile(key);
        return ResponseEntity.noContent().build();
    }
}
package com.skhu.mid_skhu.app.service.event;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.skhu.mid_skhu.app.dto.event.responseDto.S3UploadResponse;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.global.util.GetS3Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class S3ImageFileServiceImpl implements S3ImageFileService {

    private final AmazonS3 amazonS3Client;

    @Value("${app.s3.bucket}")
    private String bucket;

    /** 컨트롤러가 사용하는 신규 메서드(권장) */
    public S3UploadResponse uploadImage(MultipartFile imageFile, String directory) {
        final String normalizedDir = normalize(directory);
        final String fileName = createImageFileName(imageFile.getOriginalFilename());
        final String key = normalizedDir.isEmpty() ? fileName : normalizedDir + "/" + fileName;

        try (InputStream inputStream = imageFile.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imageFile.getContentType());
            metadata.setContentLength(imageFile.getSize());
            amazonS3Client.putObject(bucket, key, inputStream, metadata);

            URL url = amazonS3Client.getUrl(bucket, key); // LocalStack에선 접근 불가일 수 있음
            return new S3UploadResponse(
                    bucket,
                    key,
                    url != null ? url.toString() : null,
                    imageFile.getSize(),
                    imageFile.getContentType()
            );
        } catch (Exception e) {
            throw new CustomException(
                    ErrorCode.FAILED_UPLOAD_IMAGE_FILE_EXCEPTION,
                    "S3 업로드 실패: " + e.getMessage()
            );
        }
    }

    // 기존 메서드(호환 유지). 내부적으로 신규 로직을 재사용
    public GetS3Resource uploadSingleImageFile(MultipartFile imageFile, String directory) {
        S3UploadResponse res = uploadImage(imageFile, directory);
        // 과거 타입과의 호환을 위해 최소 정보만 매핑
        return new GetS3Resource(res.url(), fileNameFromKey(res.key()));
    }

    @Override
    public List<GetS3Resource> uploadImageFiles(List<MultipartFile> imageFiles, String directory) {
        return imageFiles.stream()
                .map(file -> uploadSingleImageFile(file, directory))
                .collect(Collectors.toList());
    }

    /** key 전체를 받아 삭제 (이전 deleteFile(fileName)도 key로 동작했다면 명확화 차원에서 동일 시그니처 유지) */
    @Override
    public void deleteFile(String key) {
        amazonS3Client.deleteObject(bucket, key);
    }

    private String normalize(String dir) {
        if (dir == null) return "";
        String n = dir.replace("\\", "/");
        n = n.replaceAll("^/+", "").replaceAll("/+$", "");
        return n;
    }

    private String createImageFileName(String original) {
        return UUID.randomUUID() + getFileExtensionSafe(original);
    }

    private String getFileExtensionSafe(String name) {
        if (name == null) return "";
        int i = name.lastIndexOf('.');
        return (i >= 0 && i < name.length() - 1) ? name.substring(i).toLowerCase() : "";
    }

    private String fileNameFromKey(String key) {
        if (key == null) return null;
        int i = key.lastIndexOf('/');
        return (i >= 0) ? key.substring(i + 1) : key;
    }
}
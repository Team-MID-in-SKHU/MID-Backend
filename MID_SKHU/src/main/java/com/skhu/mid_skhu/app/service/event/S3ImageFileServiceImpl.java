package com.skhu.mid_skhu.app.service.event;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.skhu.mid_skhu.app.dto.event.responseDto.PreSignedUploadResponse;
import com.skhu.mid_skhu.app.dto.event.responseDto.S3UploadResponse;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import com.skhu.mid_skhu.global.util.GetS3Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Override
    public PreSignedUploadResponse createPreSignedUploadUrl(String directory, 
                                                            String originalFileName,
                                                            String contentType) {

        final String normalizedDirectory = normalize(directory);
        final String fileName = createImageFileName(originalFileName);
        final String key = getKey(normalizedDirectory, fileName);

        Instant expireAt = Instant.now().plus(Duration.ofMinutes(15));

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, key)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(Date.from(expireAt));

        if (contentType != null && !contentType.isBlank()) {
            generatePresignedUrlRequest.addRequestParameter("Content-Type", contentType);
        }

        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return new PreSignedUploadResponse(
                bucket,
                key,
                url.toString(),
                expireAt,
                HttpMethod.PUT.name(),
                contentType
        );
    }

    private String getKey(String normalizedDirectory, String fileName) {
        if (normalizedDirectory.isEmpty()) {
            return fileName;
        }

        return normalizedDirectory + "/" + fileName;
    }

    @Override
    public S3UploadResponse uploadMultipartImage(MultipartFile imageFile, String directory) {
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

    public GetS3Resource uploadSingleImageFile(MultipartFile imageFile, String directory) {
        S3UploadResponse res = uploadMultipartImage(imageFile, directory);
        // 과거 타입과의 호환을 위해 최소 정보만 매핑
        return new GetS3Resource(res.url(), fileNameFromKey(res.key()));
    }

    @Override
    public List<GetS3Resource> uploadImageFiles(List<MultipartFile> imageFiles, String directory) {
        return imageFiles.stream()
                .map(file -> uploadSingleImageFile(file, directory))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(String key) {
        amazonS3Client.deleteObject(bucket, key);
    }

    private String normalize(String dir) {
        if (dir == null) {
            return "";
        }
        String directory = dir.replace("\\", "/");
        directory = directory.replaceAll("^/+", "").replaceAll("/+$", "");
        return directory;
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
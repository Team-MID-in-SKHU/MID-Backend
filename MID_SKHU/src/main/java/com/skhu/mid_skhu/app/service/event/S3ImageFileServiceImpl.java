package com.skhu.mid_skhu.app.service.event;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.skhu.mid_skhu.global.exception.ErrorCode;
import com.skhu.mid_skhu.global.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class S3ImageFileServiceImpl {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImageFile(MultipartFile imageFile, String directory) throws IOException {
        String imageFileName = sanitizeImageFileName(imageFile.getOriginalFilename());
        String imageFileUrl = "https://" + bucket + "/" + directory + "/" + imageFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());

        amazonS3Client.putObject(bucket, directory + "/" + imageFileName, imageFile.getInputStream(), metadata);

        return imageFileUrl;
    }

    private String sanitizeImageFileName(String imageFileName) {
        String encodedImageFileName;
        try {
            encodedImageFileName = URLEncoder.encode(imageFileName, StandardCharsets.UTF_8.toString());
            return encodedImageFileName.replaceAll("/", "");
        } catch (UnsupportedEncodingException e) {
            throw new CustomException(ErrorCode.FAIL_ENCODING_IMAGE_FILE_NAME,
                    ErrorCode.FAIL_ENCODING_IMAGE_FILE_NAME.getMessage());
        }
    }
}

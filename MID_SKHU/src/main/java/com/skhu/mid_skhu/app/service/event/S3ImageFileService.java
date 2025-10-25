package com.skhu.mid_skhu.app.service.event;

import com.skhu.mid_skhu.app.dto.event.responseDto.S3UploadResponse;
import com.skhu.mid_skhu.global.util.GetS3Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3ImageFileService {
    S3UploadResponse uploadImage(MultipartFile imageFile, String directory);
    List<GetS3Resource> uploadImageFiles(List<MultipartFile> imageFiles, String directory);
    void deleteFile(String imageFileName);
}

package com.skhu.mid_skhu.app.service.event;

import com.skhu.mid_skhu.global.util.GetS3Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3ImageFileService {
    GetS3Resource uploadSingleImageFile(MultipartFile imageFile, String directory) throws IOException;
    List<GetS3Resource> uploadImageFiles(List<MultipartFile> imageFiles, String directory);
}

package com.skhu.mid_skhu.app.service.event;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3ImageFileService {
    String uploadImageFile(MultipartFile imageFile, String directory) throws IOException;
    List<String> uploadImageFiles(List<MultipartFile> imageFiles, String directory);
}

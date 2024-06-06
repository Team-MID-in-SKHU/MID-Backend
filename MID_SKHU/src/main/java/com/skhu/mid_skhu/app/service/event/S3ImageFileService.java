package com.skhu.mid_skhu.app.service.event;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3ImageFileService {
    String uploadImageFile(MultipartFile imageFile, String directory) throws IOException;
}

package com.techblog.backend.service.publicInterface;

import com.techblog.backend.dto.image.ImageResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;

public interface ImageService {
    ImageResponseDto saveImage(String username, byte[] imageData, String imageName, String contentType, HttpServletRequest request);

    InputStream getImage(String username, String imageName);

    String deleteImage(String username, String imageName);
}

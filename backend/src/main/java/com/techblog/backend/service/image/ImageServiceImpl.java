package com.techblog.backend.service.image;

import com.techblog.backend.dto.image.ImageResponseDto;
import com.techblog.backend.exception.all.IlegalArgumentException;
import com.techblog.backend.service.publicInterface.file.FileStorageService;
import com.techblog.backend.service.publicInterface.file.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {
    Set<String> allowedImageTypes = Set.of("image/jpeg", "image/png", "image/gif", "image/jpg", "image/webp");
    private final FileStorageService fileStorageService;

    public ImageServiceImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ImageResponseDto saveImage(String username, byte[] imageData, String imageName, String contentType, HttpServletRequest request) {

        if (imageData == null || !this.allowedImageTypes.contains(contentType)) {
            throw new IlegalArgumentException("Invalid image data or content type");
        }

        fileStorageService.uploadFile(
                "images" + "." + username,
                imageName,
                new ByteArrayInputStream(imageData),
                (long) imageData.length,
                contentType
        );

        StringBuilder imageUrl = new StringBuilder(request.getScheme());

        imageUrl.append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append("/api/v1/images/")
                .append(username)
                .append("/")
                .append(imageName);

        return new ImageResponseDto(
                imageName,
                imageUrl.toString(),
                contentType,
                (long) imageData.length
        );
    }

    @Override
    public InputStream getImage(String username, String imageName) {
        return fileStorageService.getFile(
                "images" + "." + username,
                imageName
        );
    }

    @Override
    public String deleteImage(String username, String imageName) {
        fileStorageService.deleteFile(
                "images" + "." + username,
                imageName
        );
        return "Image deleted successfully";
    }
}

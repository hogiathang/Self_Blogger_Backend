package com.techblog.backend.service.image;

import com.techblog.backend.service.publicInterface.FileStorageService;
import com.techblog.backend.service.publicInterface.ImageService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class ImageServiceImpl implements ImageService {

    private final FileStorageService fileStorageService;

    public ImageServiceImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public String saveImage(String username, byte[] imageData, String imageName) {

        return fileStorageService.uploadFile(
                "images" + "." + username,
                imageName,
                new ByteArrayInputStream(imageData),
                (long) imageData.length,
                "image/jpeg"
        );
    }

    @Override
    public String getImage(String username, String imageName) {
        return fileStorageService.getFileUrl(
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

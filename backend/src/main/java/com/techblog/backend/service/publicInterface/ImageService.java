package com.techblog.backend.service.publicInterface;

public interface ImageService {
    String saveImage(String username, byte[] imageData, String imageName);

    String getImage(String username, String imageName);

    String deleteImage(String username, String imageName);
}

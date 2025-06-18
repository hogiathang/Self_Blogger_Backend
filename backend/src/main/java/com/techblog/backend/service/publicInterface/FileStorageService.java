package com.techblog.backend.service.publicInterface;

import java.io.InputStream;

public interface FileStorageService {
    String uploadFile(String subBucket, String fileName, InputStream fileData, Long fileSize, String contentType);
    String getFileUrl(String subBucket, String fileName);
    InputStream getFile(String subBucket, String fileName);
    void deleteFile(String subBucket, String fileName);
}

package com.techblog.backend.service.publicInterface.file;

import java.io.InputStream;

public interface FileStorageService {
    String uploadFile(String subBucket, String fileName, InputStream fileData, Long fileSize, String contentType);
    String getFileUrl(String subBucket, String fileName);
    InputStream getFile(String subBucket, String fileName);
    boolean deleteFile(String subBucket, String fileName);
}
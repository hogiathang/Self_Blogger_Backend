package com.techblog.backend.service.fileStorage;

import com.techblog.backend.service.publicInterface.file.FileStorageService;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

/**
 * Lớp này sử dụng Minio để lưu trữ file
 */
@Service
public class MinioStorage implements FileStorageService {

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.secret-key}")
    private String minioSecretKey;

    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        this.minioClient = MinioClient.builder()
                .endpoint(this.minioEndpoint)
                .credentials(this.minioAccessKey, this.minioSecretKey)
                .build();
    }

    /**
     * Hàm này dùng để tải file lên Minio
     * @param bucket tên bucket nơi lưu trữ file
     * @param fileName tên file sẽ được lưu trữ
     * @param fileData dữ liệu của file dưới dạng InputStream
     * @param fileSize kích thước của file
     * @return đường dẫn truy cập đến file đã tải lên
     */
    @Override
    public String uploadFile(String bucket, String fileName, InputStream fileData, Long fileSize, String contentType) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
            ObjectWriteResponse response = minioClient.putObject(
                    PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(fileData, fileSize, -1)
                        .contentType("application/octet-stream")
                            .build()
            );

            if (response != null) {
                return minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .bucket(bucket)
                                .object(fileName)
                                .method(Method.GET)
                                .extraQueryParams(Map.of(
                                        "response-content-type", contentType,
                                        "response-content-disposition", "inline"
                                ))
                                .build()
                );
            } else {
                throw new RuntimeException("Lỗi tải file lên Minio: Không có phản hồi từ Minio");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hàm này dùng để lấy URL của file đã tải lên
     * @param bucket tên bucket nơi lưu trữ file
     * @param fileName tên file cần lấy URL
     * @return URL của file đã tải lên
     */
    @Override
    public String getFileUrl(String bucket, String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hàm này dùng để tải file từ Minio về
     * @param bucket tên bucket nơi lưu trữ file
     * @param fileName tên file cần tải xuống
     * @return InputStream của file đã tải xuống
     */
    @Override
    public InputStream getFile(String bucket, String fileName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                throw new RuntimeException("Bucket không tồn tại: " + bucket);
            } else {
                // Lấy đối tượng từ Minio
                GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .build();
                return minioClient.getObject(getObjectArgs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hàm này dùng để xóa file khỏi Minio
     * @param subBucket tên bucket nơi lưu trữ file
     * @param fileName tên file cần xóa
     */
    @Override
    public void deleteFile(String subBucket, String fileName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(subBucket).build())) {
                throw new RuntimeException("Bucket không tồn tại: " + subBucket);
            } else {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(subBucket)
                        .object(fileName)
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

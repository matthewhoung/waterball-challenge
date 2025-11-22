package tw.waterballsa.challenge.infrastructure.storage;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @PostConstruct
    public void init() {
        try {
            createBucketIfNotExists();
            logger.info("MinIO initialized successfully with bucket: {}", bucketName);
        } catch (Exception e) {
            logger.error("Failed to initialize MinIO", e);
            throw new RuntimeException("MinIO initialization failed", e);
        }
    }

    private void createBucketIfNotExists() throws Exception {
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
            );
            logger.info("Created new bucket: {}", bucketName);
        }
    }

    /**
     * Upload video file and return the object key
     */
    public String uploadVideo(MultipartFile file, String folder) {
        validateFile(file);

        String fileName = generateFileName(file.getOriginalFilename());
        String objectName = folder + "/" + fileName;

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            logger.info("Uploaded video: {}", objectName);
            return objectName;

        } catch (Exception e) {
            logger.error("Failed to upload video: {}", objectName, e);
            throw new RuntimeException("Failed to upload video", e);
        }
    }

    /**
     * Upload thumbnail/image file
     */
    public String uploadImage(MultipartFile file, String folder) {
        validateFile(file);

        String fileName = generateFileName(file.getOriginalFilename());
        String objectName = folder + "/" + fileName;

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            logger.info("Uploaded image: {}", objectName);
            return objectName;

        } catch (Exception e) {
            logger.error("Failed to upload image: {}", objectName, e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    /**
     * Get presigned URL for video streaming (valid for 7 days)
     */
    public String getVideoUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            logger.error("Failed to generate video URL for: {}", objectName, e);
            throw new RuntimeException("Failed to generate video URL", e);
        }
    }

    /**
     * Get presigned URL for image (valid for 7 days)
     */
    public String getImageUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            logger.error("Failed to generate image URL for: {}", objectName, e);
            throw new RuntimeException("Failed to generate image URL", e);
        }
    }

    /**
     * Delete file from MinIO
     */
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            logger.info("Deleted file: {}", objectName);
        } catch (Exception e) {
            logger.error("Failed to delete file: {}", objectName, e);
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    /**
     * Check if file exists
     */
    public boolean fileExists(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get file metadata
     */
    public FileMetadata getFileMetadata(String objectName) {
        try {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );

            return new FileMetadata(
                    objectName,
                    stat.size(),
                    stat.contentType()
            );
        } catch (Exception e) {
            logger.error("Failed to get file metadata for: {}", objectName, e);
            throw new RuntimeException("Failed to get file metadata", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
    }

    private String generateFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    public static class FileMetadata {
        private final String objectName;
        private final long size;
        private final String contentType;

        public FileMetadata(String objectName, long size, String contentType) {
            this.objectName = objectName;
            this.size = size;
            this.contentType = contentType;
        }

        public String getObjectName() {
            return objectName;
        }

        public long getSize() {
            return size;
        }

        public String getContentType() {
            return contentType;
        }
    }
}
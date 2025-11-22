package tw.waterballsa.challenge.infrastructure.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tw.waterballsa.challenge.common.exceptions.ValidationException;

import java.util.Arrays;
import java.util.List;

@Component
public class FileValidator {

    @Value("${minio.max-file-size}")
    private long maxFileSize;

    @Value("${minio.allowed-video-types}")
    private String allowedVideoTypes;

    @Value("${minio.allowed-image-types}")
    private String allowedImageTypes;

    public void validateVideoFile(MultipartFile file) {
        validateFileNotEmpty(file);
        validateFileSize(file);
        validateVideoContentType(file);
    }

    public void validateImageFile(MultipartFile file) {
        validateFileNotEmpty(file);
        validateFileSize(file);
        validateImageContentType(file);
    }

    private void validateFileNotEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("File cannot be empty");
        }
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > maxFileSize) {
            throw new ValidationException(
                    String.format("File size exceeds maximum allowed size of %d bytes", maxFileSize)
            );
        }
    }

    private void validateVideoContentType(MultipartFile file) {
        String contentType = file.getContentType();
        List<String> allowed = Arrays.asList(allowedVideoTypes.split(","));

        if (contentType == null || !allowed.contains(contentType)) {
            throw new ValidationException(
                    "Invalid video format. Allowed formats: " + allowedVideoTypes
            );
        }
    }

    private void validateImageContentType(MultipartFile file) {
        String contentType = file.getContentType();
        List<String> allowed = Arrays.asList(allowedImageTypes.split(","));

        if (contentType == null || !allowed.contains(contentType)) {
            throw new ValidationException(
                    "Invalid image format. Allowed formats: " + allowedImageTypes
            );
        }
    }
}
package tw.waterballsa.challenge.features.lesson.UploadVideo;

import org.springframework.web.multipart.MultipartFile;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.util.UUID;

public class UploadVideoCommand implements Command<UploadVideoResponse> {
    private final UUID lessonId;
    private final UUID currentUserId;
    private final MultipartFile videoFile;
    private final MultipartFile thumbnailFile;

    public UploadVideoCommand(UUID lessonId, UUID currentUserId, MultipartFile videoFile,
                              MultipartFile thumbnailFile) {
        this.lessonId = lessonId;
        this.currentUserId = currentUserId;
        this.videoFile = videoFile;
        this.thumbnailFile = thumbnailFile;
    }

    public UUID getLessonId() { return lessonId; }
    public UUID getCurrentUserId() { return currentUserId; }
    public MultipartFile getVideoFile() { return videoFile; }
    public MultipartFile getThumbnailFile() { return thumbnailFile; }
}
package tw.waterballsa.challenge.features.lesson.UploadVideo;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.common.exceptions.BusinessException;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.common.exceptions.ValidationException;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;
import tw.waterballsa.challenge.contexts.lesson.domain.Lesson;
import tw.waterballsa.challenge.contexts.lesson.domain.Video;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.VideoStatus;
import tw.waterballsa.challenge.contexts.lesson.repository.LessonRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;
import tw.waterballsa.challenge.infrastructure.storage.FileValidator;
import tw.waterballsa.challenge.infrastructure.storage.MinioService;
import tw.waterballsa.challenge.infrastructure.storage.StorageFolders;

@Component
public class UploadVideoHandler implements CommandHandler<UploadVideoCommand, UploadVideoResponse> {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final MinioService minioService;
    private final FileValidator fileValidator;

    public UploadVideoHandler(LessonRepository lessonRepository, UserRepository userRepository,
                              MinioService minioService, FileValidator fileValidator) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.minioService = minioService;
        this.fileValidator = fileValidator;
    }

    @Override
    @Transactional
    public UploadVideoResponse handle(UploadVideoCommand command) {
        Lesson lesson = lessonRepository.findByIdWithContent(command.getLessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        User currentUser = userRepository.findById(command.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check permissions
        if (!lesson.getCourse().getInstructor().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != Roles.ADMIN) {
            throw new BusinessException("You don't have permission to upload video to this lesson");
        }

        // Validate lesson type
        if (lesson.getLessonType() != LessonType.VIDEO && lesson.getLessonType() != LessonType.MIXED) {
            throw new ValidationException("This lesson does not support video content");
        }

        // Validate video file
        fileValidator.validateVideoFile(command.getVideoFile());

        // Upload video
        String videoObjectName = minioService.uploadVideo(command.getVideoFile(), StorageFolders.VIDEOS);
        String videoUrl = minioService.getVideoUrl(videoObjectName);

        // Upload thumbnail if provided
        String thumbnailUrl = null;
        if (command.getThumbnailFile() != null && !command.getThumbnailFile().isEmpty()) {
            fileValidator.validateImageFile(command.getThumbnailFile());
            String thumbnailObjectName = minioService.uploadImage(command.getThumbnailFile(), StorageFolders.THUMBNAILS);
            thumbnailUrl = minioService.getImageUrl(thumbnailObjectName);
        }

        // Update video entity
        Video video = lesson.getVideo();
        if (video == null) {
            throw new ValidationException("Video entity not found for this lesson");
        }

        video.setVideoUrl(videoObjectName);
        video.setThumbnailUrl(thumbnailUrl != null ? thumbnailUrl : video.getThumbnailUrl());
        video.setFileSize(command.getVideoFile().getSize());
        video.setStatus(VideoStatus.READY);

        lessonRepository.save(lesson);

        return new UploadVideoResponse(
                video.getId(),
                videoUrl,
                thumbnailUrl,
                "Video uploaded successfully"
        );
    }
}
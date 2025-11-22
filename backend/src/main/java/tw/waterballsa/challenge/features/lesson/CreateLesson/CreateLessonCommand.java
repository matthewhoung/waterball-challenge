package tw.waterballsa.challenge.features.lesson.CreateLesson;

import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;
import tw.waterballsa.challenge.features.lesson.common.LessonResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.util.UUID;

public class CreateLessonCommand implements Command<LessonResponse> {
    private final UUID courseId;
    private final UUID currentUserId;
    private final String title;
    private final String description;
    private final LessonType lessonType;
    private final Integer displayOrder;
    private final Integer estimatedMinutes;
    private final String articleContent;
    private final Integer videoDuration;

    public CreateLessonCommand(UUID courseId, UUID currentUserId, String title, String description,
                               LessonType lessonType, Integer displayOrder, Integer estimatedMinutes,
                               String articleContent, Integer videoDuration) {
        this.courseId = courseId;
        this.currentUserId = currentUserId;
        this.title = title;
        this.description = description;
        this.lessonType = lessonType;
        this.displayOrder = displayOrder;
        this.estimatedMinutes = estimatedMinutes;
        this.articleContent = articleContent;
        this.videoDuration = videoDuration;
    }

    public UUID getCourseId() { return courseId; }
    public UUID getCurrentUserId() { return currentUserId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LessonType getLessonType() { return lessonType; }
    public Integer getDisplayOrder() { return displayOrder; }
    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public String getArticleContent() { return articleContent; }
    public Integer getVideoDuration() { return videoDuration; }
}
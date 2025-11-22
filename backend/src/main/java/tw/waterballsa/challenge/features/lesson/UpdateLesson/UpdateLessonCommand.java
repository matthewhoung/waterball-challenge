package tw.waterballsa.challenge.features.lesson.UpdateLesson;

import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonStatus;
import tw.waterballsa.challenge.features.lesson.common.LessonResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.util.UUID;

public class UpdateLessonCommand implements Command<LessonResponse> {
    private final UUID lessonId;
    private final UUID currentUserId;
    private final String title;
    private final String description;
    private final Integer displayOrder;
    private final Integer estimatedMinutes;
    private final LessonStatus status;
    private final String articleContent;

    public UpdateLessonCommand(UUID lessonId, UUID currentUserId, String title, String description,
                               Integer displayOrder, Integer estimatedMinutes, LessonStatus status,
                               String articleContent) {
        this.lessonId = lessonId;
        this.currentUserId = currentUserId;
        this.title = title;
        this.description = description;
        this.displayOrder = displayOrder;
        this.estimatedMinutes = estimatedMinutes;
        this.status = status;
        this.articleContent = articleContent;
    }

    public UUID getLessonId() { return lessonId; }
    public UUID getCurrentUserId() { return currentUserId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getDisplayOrder() { return displayOrder; }
    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public LessonStatus getStatus() { return status; }
    public String getArticleContent() { return articleContent; }
}
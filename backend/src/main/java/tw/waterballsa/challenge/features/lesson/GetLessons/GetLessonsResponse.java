package tw.waterballsa.challenge.features.lesson.GetLessons;

import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonStatus;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;

import java.util.UUID;

public class GetLessonsResponse {
    private UUID id;
    private String title;
    private String description;
    private LessonType lessonType;
    private Integer displayOrder;
    private Integer estimatedMinutes;
    private LessonStatus status;
    private boolean hasArticle;
    private boolean hasVideo;

    public GetLessonsResponse() {}

    public GetLessonsResponse(UUID id, String title, String description, LessonType lessonType,
                              Integer displayOrder, Integer estimatedMinutes, LessonStatus status,
                              boolean hasArticle, boolean hasVideo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lessonType = lessonType;
        this.displayOrder = displayOrder;
        this.estimatedMinutes = estimatedMinutes;
        this.status = status;
        this.hasArticle = hasArticle;
        this.hasVideo = hasVideo;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LessonType getLessonType() { return lessonType; }
    public void setLessonType(LessonType lessonType) { this.lessonType = lessonType; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }
    public LessonStatus getStatus() { return status; }
    public void setStatus(LessonStatus status) { this.status = status; }
    public boolean isHasArticle() { return hasArticle; }
    public void setHasArticle(boolean hasArticle) { this.hasArticle = hasArticle; }
    public boolean isHasVideo() { return hasVideo; }
    public void setHasVideo(boolean hasVideo) { this.hasVideo = hasVideo; }
}
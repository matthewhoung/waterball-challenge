package tw.waterballsa.challenge.features.lesson.CreateLesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateLessonRequest {

    @NotNull(message = "Course ID is required")
    private String courseId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Lesson type is required")
    private String lessonType; // ARTICLE, VIDEO, MIXED

    private Integer displayOrder;
    private Integer estimatedMinutes;

    // For ARTICLE type
    private String articleContent;

    // For VIDEO type - will be uploaded separately
    private Integer videoDuration;

    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLessonType() { return lessonType; }
    public void setLessonType(String lessonType) { this.lessonType = lessonType; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }
    public String getArticleContent() { return articleContent; }
    public void setArticleContent(String articleContent) { this.articleContent = articleContent; }
    public Integer getVideoDuration() { return videoDuration; }
    public void setVideoDuration(Integer videoDuration) { this.videoDuration = videoDuration; }
}
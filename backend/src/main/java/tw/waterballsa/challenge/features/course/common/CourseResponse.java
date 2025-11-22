package tw.waterballsa.challenge.features.course.common;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;

import java.util.UUID;

public class CourseResponse {
    private UUID id;
    private String title;
    private String description;
    private String coverImageUrl;
    private UUID instructorId;
    private String instructorName;
    private String category;
    private CourseLevel level;
    private Integer estimatedHours;
    private Integer displayOrder;
    private CourseStatus status;
    private Integer lessonCount;

    public CourseResponse() {}

    public CourseResponse(UUID id, String title, String description, String coverImageUrl,
                          UUID instructorId, String instructorName, String category,
                          CourseLevel level, Integer estimatedHours, Integer displayOrder,
                          CourseStatus status, Integer lessonCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.category = category;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.displayOrder = displayOrder;
        this.status = status;
        this.lessonCount = lessonCount;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public UUID getInstructorId() { return instructorId; }
    public void setInstructorId(UUID instructorId) { this.instructorId = instructorId; }
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public CourseLevel getLevel() { return level; }
    public void setLevel(CourseLevel level) { this.level = level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }
    public Integer getLessonCount() { return lessonCount; }
    public void setLessonCount(Integer lessonCount) { this.lessonCount = lessonCount; }
}
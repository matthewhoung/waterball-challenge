package tw.waterballsa.challenge.features.course.GetCourses;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;

import java.util.UUID;

public class GetCoursesResponse {
    private UUID id;
    private String title;
    private String description;
    private String coverImageUrl;
    private String instructorName;
    private String category;
    private CourseLevel level;
    private Integer estimatedHours;
    private CourseStatus status;
    private Integer lessonCount;

    public GetCoursesResponse() {}

    public GetCoursesResponse(UUID id, String title, String description, String coverImageUrl,
                              String instructorName, String category, CourseLevel level,
                              Integer estimatedHours, CourseStatus status, Integer lessonCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.instructorName = instructorName;
        this.category = category;
        this.level = level;
        this.estimatedHours = estimatedHours;
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
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public CourseLevel getLevel() { return level; }
    public void setLevel(CourseLevel level) { this.level = level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }
    public Integer getLessonCount() { return lessonCount; }
    public void setLessonCount(Integer lessonCount) { this.lessonCount = lessonCount; }
}
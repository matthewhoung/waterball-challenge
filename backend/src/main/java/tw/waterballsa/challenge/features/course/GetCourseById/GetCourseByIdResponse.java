package tw.waterballsa.challenge.features.course.GetCourseById;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;

import java.util.List;
import java.util.UUID;

public class GetCourseByIdResponse {
    private UUID id;
    private String title;
    private String description;
    private String coverImageUrl;
    private UUID instructorId;
    private String instructorName;
    private String category;
    private CourseLevel level;
    private Integer estimatedHours;
    private CourseStatus status;
    private List<LessonInCourse> lessons;

    public static class LessonInCourse {
        private UUID id;
        private String title;
        private String description;
        private LessonType lessonType;
        private Integer displayOrder;
        private Integer estimatedMinutes;

        public LessonInCourse() {}

        public LessonInCourse(UUID id, String title, String description, LessonType lessonType,
                              Integer displayOrder, Integer estimatedMinutes) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.lessonType = lessonType;
            this.displayOrder = displayOrder;
            this.estimatedMinutes = estimatedMinutes;
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
    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }
    public List<LessonInCourse> getLessons() { return lessons; }
    public void setLessons(List<LessonInCourse> lessons) { this.lessons = lessons; }
}
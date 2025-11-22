package tw.waterballsa.challenge.features.course.UpdateCourse;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;
import tw.waterballsa.challenge.features.course.common.CourseResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.util.UUID;

public class UpdateCourseCommand implements Command<CourseResponse> {
    private final UUID courseId;
    private final UUID currentUserId;
    private final String title;
    private final String description;
    private final String category;
    private final CourseLevel level;
    private final Integer estimatedHours;
    private final Integer displayOrder;
    private final CourseStatus status;

    public UpdateCourseCommand(UUID courseId, UUID currentUserId, String title, String description,
                               String category, CourseLevel level, Integer estimatedHours,
                               Integer displayOrder, CourseStatus status) {
        this.courseId = courseId;
        this.currentUserId = currentUserId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.displayOrder = displayOrder;
        this.status = status;
    }

    public UUID getCourseId() { return courseId; }
    public UUID getCurrentUserId() { return currentUserId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public CourseLevel getLevel() { return level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public Integer getDisplayOrder() { return displayOrder; }
    public CourseStatus getStatus() { return status; }
}
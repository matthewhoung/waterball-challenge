package tw.waterballsa.challenge.features.course.CreateCourse;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.features.course.common.CourseResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.util.UUID;

public class CreateCourseCommand implements Command<CourseResponse> {
    private final String title;
    private final String description;
    private final String category;
    private final CourseLevel level;
    private final Integer estimatedHours;
    private final UUID instructorId;

    public CreateCourseCommand(String title, String description, String category,
                               CourseLevel level, Integer estimatedHours, UUID instructorId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.instructorId = instructorId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public CourseLevel getLevel() { return level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public UUID getInstructorId() { return instructorId; }
}
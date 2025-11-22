package tw.waterballsa.challenge.features.course.CreateCourse;

import jakarta.validation.constraints.NotBlank;

public class CreateCourseRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String category;
    private String level;
    private Integer estimatedHours;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
}
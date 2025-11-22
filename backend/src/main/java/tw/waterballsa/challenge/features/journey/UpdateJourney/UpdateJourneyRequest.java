package tw.waterballsa.challenge.features.journey.UpdateJourney;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;

import java.math.BigDecimal;

public class UpdateJourneyRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private JourneyStatus status;
    private CourseLevel level;
    private Integer estimatedHours;
    private Integer displayOrder;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public JourneyStatus getStatus() { return status; }
    public void setStatus(JourneyStatus status) { this.status = status; }
    public CourseLevel getLevel() { return level; }
    public void setLevel(CourseLevel level) { this.level = level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
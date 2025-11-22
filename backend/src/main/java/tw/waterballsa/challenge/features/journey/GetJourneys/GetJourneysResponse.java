package tw.waterballsa.challenge.features.journey.GetJourneys;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class GetJourneysResponse {
    private UUID id;
    private String title;
    private String description;
    private String coverImageUrl;
    private BigDecimal price;
    private JourneyStatus status;
    private CourseLevel level;
    private Integer estimatedHours;
    private Integer courseCount;

    public GetJourneysResponse() {}

    public GetJourneysResponse(UUID id, String title, String description, String coverImageUrl,
                               BigDecimal price, JourneyStatus status, CourseLevel level,
                               Integer estimatedHours, Integer courseCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.price = price;
        this.status = status;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.courseCount = courseCount;
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
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public JourneyStatus getStatus() { return status; }
    public void setStatus(JourneyStatus status) { this.status = status; }
    public CourseLevel getLevel() { return level; }
    public void setLevel(CourseLevel level) { this.level = level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
    public Integer getCourseCount() { return courseCount; }
    public void setCourseCount(Integer courseCount) { this.courseCount = courseCount; }
}
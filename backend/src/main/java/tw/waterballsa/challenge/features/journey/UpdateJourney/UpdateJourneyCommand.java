package tw.waterballsa.challenge.features.journey.UpdateJourney;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;
import tw.waterballsa.challenge.features.journey.common.JourneyResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateJourneyCommand implements Command<JourneyResponse> {
    private final UUID journeyId;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final JourneyStatus status;
    private final CourseLevel level;
    private final Integer estimatedHours;
    private final Integer displayOrder;

    public UpdateJourneyCommand(UUID journeyId, String title, String description, BigDecimal price,
                                JourneyStatus status, CourseLevel level, Integer estimatedHours,
                                Integer displayOrder) {
        this.journeyId = journeyId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.status = status;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.displayOrder = displayOrder;
    }

    public UUID getJourneyId() { return journeyId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public JourneyStatus getStatus() { return status; }
    public CourseLevel getLevel() { return level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public Integer getDisplayOrder() { return displayOrder; }
}
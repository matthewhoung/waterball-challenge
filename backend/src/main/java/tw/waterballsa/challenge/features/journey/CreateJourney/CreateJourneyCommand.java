package tw.waterballsa.challenge.features.journey.CreateJourney;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.features.journey.common.JourneyResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.math.BigDecimal;

public class CreateJourneyCommand implements Command<JourneyResponse> {
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final CourseLevel level;
    private final Integer estimatedHours;
    private final Integer displayOrder;

    public CreateJourneyCommand(String title, String description, BigDecimal price,
                                CourseLevel level, Integer estimatedHours, Integer displayOrder) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.displayOrder = displayOrder;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public CourseLevel getLevel() { return level; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public Integer getDisplayOrder() { return displayOrder; }
}
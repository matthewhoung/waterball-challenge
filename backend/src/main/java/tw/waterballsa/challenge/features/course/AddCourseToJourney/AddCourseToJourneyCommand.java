package tw.waterballsa.challenge.features.course.AddCourseToJourney;

import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.util.UUID;

public class AddCourseToJourneyCommand implements Command<Void> {
    private final UUID journeyId;
    private final UUID courseId;

    public AddCourseToJourneyCommand(UUID journeyId, UUID courseId) {
        this.journeyId = journeyId;
        this.courseId = courseId;
    }

    public UUID getJourneyId() {
        return journeyId;
    }

    public UUID getCourseId() {
        return courseId;
    }
}
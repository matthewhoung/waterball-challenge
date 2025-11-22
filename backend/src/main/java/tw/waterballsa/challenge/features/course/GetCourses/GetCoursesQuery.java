package tw.waterballsa.challenge.features.course.GetCourses;

import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;
import tw.waterballsa.challenge.infrastructure.cqrs.Query;

import java.util.List;
import java.util.UUID;

public class GetCoursesQuery implements Query<List<GetCoursesResponse>> {
    private final UUID journeyId;
    private final UUID instructorId;
    private final CourseStatus status;

    public GetCoursesQuery(UUID journeyId, UUID instructorId, CourseStatus status) {
        this.journeyId = journeyId;
        this.instructorId = instructorId;
        this.status = status;
    }

    public UUID getJourneyId() { return journeyId; }
    public UUID getInstructorId() { return instructorId; }
    public CourseStatus getStatus() { return status; }
}
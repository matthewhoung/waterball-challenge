package tw.waterballsa.challenge.features.course.GetCourseById;

import tw.waterballsa.challenge.infrastructure.cqrs.Query;

import java.util.UUID;

public class GetCourseByIdQuery implements Query<GetCourseByIdResponse> {
    private final UUID courseId;

    public GetCourseByIdQuery(UUID courseId) {
        this.courseId = courseId;
    }

    public UUID getCourseId() {
        return courseId;
    }
}
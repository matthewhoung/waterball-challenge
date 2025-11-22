package tw.waterballsa.challenge.features.lesson.GetLessons;

import tw.waterballsa.challenge.infrastructure.cqrs.Query;

import java.util.List;
import java.util.UUID;

public class GetLessonsQuery implements Query<List<GetLessonsResponse>> {
    private final UUID courseId;

    public GetLessonsQuery(UUID courseId) {
        this.courseId = courseId;
    }

    public UUID getCourseId() {
        return courseId;
    }
}
package tw.waterballsa.challenge.features.lesson.GetLessonById;

import tw.waterballsa.challenge.features.lesson.common.LessonResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Query;

import java.util.UUID;

public class GetLessonByIdQuery implements Query<LessonResponse> {
    private final UUID lessonId;

    public GetLessonByIdQuery(UUID lessonId) {
        this.lessonId = lessonId;
    }

    public UUID getLessonId() {
        return lessonId;
    }
}
package tw.waterballsa.challenge.features.courses.GetCourseDetail;

import tw.waterballsa.challenge.infrastructure.cqrs.Query;
import java.util.UUID;

public record GetCourseDetailQuery(UUID courseId) implements Query<CourseDetailResponse> {}
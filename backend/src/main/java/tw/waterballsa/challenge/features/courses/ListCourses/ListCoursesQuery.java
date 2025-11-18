package tw.waterballsa.challenge.features.courses.ListCourses;

import tw.waterballsa.challenge.infrastructure.cqrs.Query;
import java.util.List;

public record ListCoursesQuery(boolean publishedOnly) implements Query<List<CourseResponse>> {
    public ListCoursesQuery() {
        this(true);
    }
}
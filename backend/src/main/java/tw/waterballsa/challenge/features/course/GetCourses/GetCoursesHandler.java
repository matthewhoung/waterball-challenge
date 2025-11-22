package tw.waterballsa.challenge.features.course.GetCourses;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;
import tw.waterballsa.challenge.contexts.course.repository.CourseRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetCoursesHandler implements QueryHandler<GetCoursesQuery, List<GetCoursesResponse>> {

    private final CourseRepository courseRepository;

    public GetCoursesHandler(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public List<GetCoursesResponse> handle(GetCoursesQuery query) {
        List<Course> courses;

        if (query.getJourneyId() != null) {
            courses = courseRepository.findByJourneyId(query.getJourneyId());
        } else if (query.getInstructorId() != null) {
            CourseStatus status = query.getStatus() != null ? query.getStatus() : CourseStatus.PUBLISHED;
            courses = courseRepository.findByInstructorIdAndStatus(query.getInstructorId(), status);
        } else {
            CourseStatus status = query.getStatus() != null ? query.getStatus() : CourseStatus.PUBLISHED;
            courses = courseRepository.findByStatusOrderByDisplayOrderAsc(status);
        }

        return courses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private GetCoursesResponse mapToResponse(Course course) {
        return new GetCoursesResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCoverImage(),
                course.getInstructor().getDisplayName(),
                course.getCategory(),
                course.getLevel(),
                course.getEstimatedHours(),
                course.getStatus(),
                course.getLessons().size()
        );
    }
}
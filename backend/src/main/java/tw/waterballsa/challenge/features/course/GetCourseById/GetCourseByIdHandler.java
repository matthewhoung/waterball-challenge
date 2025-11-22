package tw.waterballsa.challenge.features.course.GetCourseById;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.course.repository.CourseRepository;
import tw.waterballsa.challenge.contexts.lesson.domain.Lesson;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetCourseByIdHandler implements QueryHandler<GetCourseByIdQuery, GetCourseByIdResponse> {

    private final CourseRepository courseRepository;

    public GetCourseByIdHandler(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public GetCourseByIdResponse handle(GetCourseByIdQuery query) {
        Course course = courseRepository.findByIdWithLessons(query.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        GetCourseByIdResponse response = new GetCourseByIdResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setCoverImageUrl(course.getCoverImage());
        response.setInstructorId(course.getInstructor().getId());
        response.setInstructorName(course.getInstructor().getDisplayName());
        response.setCategory(course.getCategory());
        response.setLevel(course.getLevel());
        response.setEstimatedHours(course.getEstimatedHours());
        response.setStatus(course.getStatus());

        List<GetCourseByIdResponse.LessonInCourse> lessons = course.getLessons().stream()
                .map(this::mapLesson)
                .collect(Collectors.toList());

        response.setLessons(lessons);

        return response;
    }

    private GetCourseByIdResponse.LessonInCourse mapLesson(Lesson lesson) {
        return new GetCourseByIdResponse.LessonInCourse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getLessonType(),
                lesson.getDisplayOrder(),
                lesson.getEstimatedMinutes()
        );
    }
}
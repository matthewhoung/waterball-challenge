package tw.waterballsa.challenge.features.courses.GetCourseDetail;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.content.repository.CourseRepository;
import tw.waterballsa.challenge.contexts.content.repository.LessonRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

@Component
public class GetCourseDetailHandler implements QueryHandler<GetCourseDetailQuery, CourseDetailResponse> {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public GetCourseDetailHandler(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public CourseDetailResponse handle(GetCourseDetailQuery query) {
        var course = courseRepository.findById(query.courseId())
                .orElseThrow(() -> new NotFoundException("Course", query.courseId()));

        var lessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(course.getId())
                .stream()
                .map(l -> new CourseDetailResponse.LessonSummary(
                        l.getId(),
                        l.getTitle(),
                        l.isChallenge(),
                        l.getOrderIndex(),
                        l.isPreview()
                ))
                .toList();

        return new CourseDetailResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getThumbnailUrl(),
                course.getPrice(),
                course.isPurchaseRequired(),
                lessons
        );
    }
}
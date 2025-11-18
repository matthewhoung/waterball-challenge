package tw.waterballsa.challenge.features.courses.ListCourses;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.contexts.content.repository.CourseRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

import java.util.List;

@Component
public class ListCoursesHandler implements QueryHandler<ListCoursesQuery, List<CourseResponse>> {

    private final CourseRepository courseRepository;

    public ListCoursesHandler(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseResponse> handle(ListCoursesQuery query) {
        var courses = query.publishedOnly()
                ? courseRepository.findByPublishedTrue()
                : courseRepository.findAll();

        return courses.stream()
                .map(c -> new CourseResponse(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getThumbnailUrl(),
                        c.getPrice(),
                        c.isPurchaseRequired(),
                        c.isIntroCourse(),
                        c.getDiscountPercentage()
                ))
                .toList();
    }
}
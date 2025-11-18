package tw.waterballsa.challenge.features.courses.CreateCourse;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.contexts.content.domain.Course;
import tw.waterballsa.challenge.contexts.content.repository.CourseRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

import java.util.UUID;

@Component
public class CreateCourseHandler implements CommandHandler<CreateCourseCommand, UUID> {

    private final CourseRepository courseRepository;

    public CreateCourseHandler(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public UUID handle(CreateCourseCommand command) {
        var course = new Course(command.title(), command.description());
        course.setPrice(command.price());
        course.setPurchaseRequired(command.purchaseRequired());
        course.setIntroCourse(command.introCourse());
        course.setDiscountPercentage(command.discountPercentage());

        courseRepository.save(course);
        return course.getId();
    }
}
package tw.waterballsa.challenge.features.course.CreateCourse;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.common.exceptions.ValidationException;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;
import tw.waterballsa.challenge.contexts.course.repository.CourseRepository;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;
import tw.waterballsa.challenge.features.course.common.CourseResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class CreateCourseHandler implements CommandHandler<CreateCourseCommand, CourseResponse> {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CreateCourseHandler(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CourseResponse handle(CreateCourseCommand command) {
        if (courseRepository.existsByTitle(command.getTitle())) {
            throw new ValidationException("Course with this title already exists");
        }

        User instructor = userRepository.findById(command.getInstructorId())
                .orElseThrow(() -> new NotFoundException("Instructor not found"));

        if (instructor.getRole() != Roles.MENTOR && instructor.getRole() != Roles.ADMIN) {
            throw new ValidationException("User must be a MENTOR or ADMIN to create courses");
        }

        Course course = new Course(
                command.getTitle(),
                command.getDescription(),
                instructor
        );

        course.setCategory(command.getCategory());
        course.setLevel(command.getLevel());
        course.setEstimatedHours(command.getEstimatedHours());
        course.setStatus(CourseStatus.DRAFT);

        course = courseRepository.save(course);

        return mapToResponse(course);
    }

    private CourseResponse mapToResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCoverImage(),
                course.getInstructor().getId(),
                course.getInstructor().getDisplayName(),
                course.getCategory(),
                course.getLevel(),
                course.getEstimatedHours(),
                course.getDisplayOrder(),
                course.getStatus(),
                course.getLessons().size()
        );
    }
}
package tw.waterballsa.challenge.features.course.UpdateCourse;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.common.exceptions.BusinessException;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.course.repository.CourseRepository;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;
import tw.waterballsa.challenge.features.course.common.CourseResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class UpdateCourseHandler implements CommandHandler<UpdateCourseCommand, CourseResponse> {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public UpdateCourseHandler(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CourseResponse handle(UpdateCourseCommand command) {
        Course course = courseRepository.findById(command.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        User currentUser = userRepository.findById(command.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check if user is instructor or admin
        if (!course.getInstructor().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != Roles.ADMIN) {
            throw new BusinessException("You don't have permission to update this course");
        }

        if (command.getTitle() != null) {
            course.setTitle(command.getTitle());
        }
        if (command.getDescription() != null) {
            course.setDescription(command.getDescription());
        }
        if (command.getCategory() != null) {
            course.setCategory(command.getCategory());
        }
        if (command.getLevel() != null) {
            course.setLevel(command.getLevel());
        }
        if (command.getEstimatedHours() != null) {
            course.setEstimatedHours(command.getEstimatedHours());
        }
        if (command.getDisplayOrder() != null) {
            course.setDisplayOrder(command.getDisplayOrder());
        }
        if (command.getStatus() != null) {
            course.setStatus(command.getStatus());
        }

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
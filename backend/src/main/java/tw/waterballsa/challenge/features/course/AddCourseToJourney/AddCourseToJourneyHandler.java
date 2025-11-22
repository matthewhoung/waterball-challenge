package tw.waterballsa.challenge.features.course.AddCourseToJourney;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.common.exceptions.BusinessException;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.course.repository.CourseRepository;
import tw.waterballsa.challenge.contexts.journey.domain.Journey;
import tw.waterballsa.challenge.contexts.journey.repository.JourneyRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class AddCourseToJourneyHandler implements CommandHandler<AddCourseToJourneyCommand, Void> {

    private final JourneyRepository journeyRepository;
    private final CourseRepository courseRepository;

    public AddCourseToJourneyHandler(JourneyRepository journeyRepository, CourseRepository courseRepository) {
        this.journeyRepository = journeyRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Void handle(AddCourseToJourneyCommand command) {
        Journey journey = journeyRepository.findById(command.getJourneyId())
                .orElseThrow(() -> new NotFoundException("Journey not found"));

        Course course = courseRepository.findById(command.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        if (journey.getCourses().contains(course)) {
            throw new BusinessException("Course already added to this journey");
        }

        journey.addCourse(course);
        journeyRepository.save(journey);

        return null;
    }
}
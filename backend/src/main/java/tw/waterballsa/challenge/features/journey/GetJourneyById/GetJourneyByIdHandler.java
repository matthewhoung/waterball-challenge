package tw.waterballsa.challenge.features.journey.GetJourneyById;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.journey.domain.Journey;
import tw.waterballsa.challenge.contexts.journey.repository.JourneyRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetJourneyByIdHandler implements QueryHandler<GetJourneyByIdQuery, GetJourneyByIdResponse> {

    private final JourneyRepository journeyRepository;

    public GetJourneyByIdHandler(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    public GetJourneyByIdResponse handle(GetJourneyByIdQuery query) {
        Journey journey = journeyRepository.findByIdWithCourses(query.getJourneyId());

        if (journey == null) {
            throw new NotFoundException("Journey not found");
        }

        GetJourneyByIdResponse response = new GetJourneyByIdResponse();
        response.setId(journey.getId());
        response.setTitle(journey.getTitle());
        response.setDescription(journey.getDescription());
        response.setCoverImageUrl(journey.getCoverImage());
        response.setPrice(journey.getPrice());
        response.setStatus(journey.getStatus());
        response.setLevel(journey.getLevel());
        response.setEstimatedHours(journey.getEstimatedHours());

        List<GetJourneyByIdResponse.CourseInJourney> courses = journey.getCourses().stream()
                .sorted(Comparator.comparing(Course::getDisplayOrder))
                .map(this::mapCourse)
                .collect(Collectors.toList());

        response.setCourses(courses);

        return response;
    }

    private GetJourneyByIdResponse.CourseInJourney mapCourse(Course course) {
        return new GetJourneyByIdResponse.CourseInJourney(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCoverImage(),
                course.getDisplayOrder(),
                course.getLessons().size()
        );
    }
}
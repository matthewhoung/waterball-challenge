package tw.waterballsa.challenge.features.journey.GetJourneys;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.contexts.journey.domain.Journey;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;
import tw.waterballsa.challenge.contexts.journey.repository.JourneyRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetJourneysHandler implements QueryHandler<GetJourneysQuery, List<GetJourneysResponse>> {

    private final JourneyRepository journeyRepository;

    public GetJourneysHandler(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetJourneysResponse> handle(GetJourneysQuery query) {
        List<Journey> journeys;

        if (query.getStatus() != null) {
            journeys = journeyRepository.findByStatusOrderByDisplayOrderAsc(query.getStatus());
        } else {
            journeys = journeyRepository.findByStatusOrderByDisplayOrderAsc(JourneyStatus.PUBLISHED);
        }

        return journeys.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private GetJourneysResponse mapToResponse(Journey journey) {
        return new GetJourneysResponse(
                journey.getId(),
                journey.getTitle(),
                journey.getDescription(),
                journey.getCoverImage(),
                journey.getPrice(),
                journey.getStatus(),
                journey.getLevel(),
                journey.getEstimatedHours(),
                journey.getCourses().size()
        );
    }
}
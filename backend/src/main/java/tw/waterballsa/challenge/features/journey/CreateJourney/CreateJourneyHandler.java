package tw.waterballsa.challenge.features.journey.CreateJourney;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.ValidationException;
import tw.waterballsa.challenge.contexts.journey.domain.Journey;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;
import tw.waterballsa.challenge.contexts.journey.repository.JourneyRepository;
import tw.waterballsa.challenge.features.journey.common.JourneyResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class CreateJourneyHandler implements CommandHandler<CreateJourneyCommand, JourneyResponse> {

    private final JourneyRepository journeyRepository;

    public CreateJourneyHandler(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    public JourneyResponse handle(CreateJourneyCommand command) {
        if (journeyRepository.existsByTitle(command.getTitle())) {
            throw new ValidationException("Journey with this title already exists");
        }

        Journey journey = new Journey(
                command.getTitle(),
                command.getDescription(),
                command.getPrice()
        );

        journey.setLevel(command.getLevel());
        journey.setEstimatedHours(command.getEstimatedHours());
        journey.setDisplayOrder(command.getDisplayOrder() != null ? command.getDisplayOrder() : 0);
        journey.setStatus(JourneyStatus.DRAFT);

        journey = journeyRepository.save(journey);

        return mapToResponse(journey);
    }

    private JourneyResponse mapToResponse(Journey journey) {
        return new JourneyResponse(
                journey.getId(),
                journey.getTitle(),
                journey.getDescription(),
                journey.getCoverImage(),
                journey.getPrice(),
                journey.getStatus(),
                journey.getLevel(),
                journey.getEstimatedHours(),
                journey.getDisplayOrder(),
                journey.getCourses().size()
        );
    }
}
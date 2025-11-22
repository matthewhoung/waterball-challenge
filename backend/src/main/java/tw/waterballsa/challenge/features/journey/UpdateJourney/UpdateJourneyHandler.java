package tw.waterballsa.challenge.features.journey.UpdateJourney;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.journey.domain.Journey;
import tw.waterballsa.challenge.contexts.journey.repository.JourneyRepository;
import tw.waterballsa.challenge.features.journey.common.JourneyResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class UpdateJourneyHandler implements CommandHandler<UpdateJourneyCommand, JourneyResponse> {

    private final JourneyRepository journeyRepository;

    public UpdateJourneyHandler(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    @Transactional
    public JourneyResponse handle(UpdateJourneyCommand command) {
        Journey journey = journeyRepository.findById(command.getJourneyId())
                .orElseThrow(() -> new NotFoundException("Journey not found"));

        if (command.getTitle() != null) {
            journey.setTitle(command.getTitle());
        }
        if (command.getDescription() != null) {
            journey.setDescription(command.getDescription());
        }
        if (command.getPrice() != null) {
            journey.setPrice(command.getPrice());
        }
        if (command.getStatus() != null) {
            journey.setStatus(command.getStatus());
        }
        if (command.getLevel() != null) {
            journey.setLevel(command.getLevel());
        }
        if (command.getEstimatedHours() != null) {
            journey.setEstimatedHours(command.getEstimatedHours());
        }
        if (command.getDisplayOrder() != null) {
            journey.setDisplayOrder(command.getDisplayOrder());
        }

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
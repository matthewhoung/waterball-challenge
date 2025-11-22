package tw.waterballsa.challenge.features.journey.GetJourneys;

import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;
import tw.waterballsa.challenge.infrastructure.cqrs.Query;

import java.util.List;

public class GetJourneysQuery implements Query<List<GetJourneysResponse>> {
    private final JourneyStatus status;

    public GetJourneysQuery(JourneyStatus status) {
        this.status = status;
    }

    public JourneyStatus getStatus() {
        return status;
    }
}
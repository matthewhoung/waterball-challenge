package tw.waterballsa.challenge.features.journey.GetJourneyById;

import tw.waterballsa.challenge.infrastructure.cqrs.Query;

import java.util.UUID;

public class GetJourneyByIdQuery implements Query<GetJourneyByIdResponse> {
    private final UUID journeyId;

    public GetJourneyByIdQuery(UUID journeyId) {
        this.journeyId = journeyId;
    }

    public UUID getJourneyId() {
        return journeyId;
    }
}
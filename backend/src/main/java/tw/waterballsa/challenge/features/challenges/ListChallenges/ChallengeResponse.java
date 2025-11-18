package tw.waterballsa.challenge.features.challenges.ListChallenges;

import java.util.UUID;

public record ChallengeResponse(
        UUID id,
        String title,
        String description,
        String prerequisites,
        Integer timeLimitDays
) {}
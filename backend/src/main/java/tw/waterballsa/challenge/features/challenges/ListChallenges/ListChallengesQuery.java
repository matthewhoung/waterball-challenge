package tw.waterballsa.challenge.features.challenges.ListChallenges;

import tw.waterballsa.challenge.infrastructure.cqrs.Query;
import java.util.List;

public record ListChallengesQuery() implements Query<List<ChallengeResponse>> {}
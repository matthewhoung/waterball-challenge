package tw.waterballsa.challenge.features.auth.GetUserDetails;

import tw.waterballsa.challenge.infrastructure.cqrs.Query;

import java.util.UUID;

public class GetUserDetailsQuery implements Query<UserDetailsResponse> {
    private final UUID userId;

    public GetUserDetailsQuery(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
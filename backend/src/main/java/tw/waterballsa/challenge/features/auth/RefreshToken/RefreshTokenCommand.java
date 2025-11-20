package tw.waterballsa.challenge.features.auth.RefreshToken;

import tw.waterballsa.challenge.infrastructure.cqrs.Command;

public class RefreshTokenCommand implements Command<RefreshTokenResponse> {
    private final String refreshToken;

    public RefreshTokenCommand(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
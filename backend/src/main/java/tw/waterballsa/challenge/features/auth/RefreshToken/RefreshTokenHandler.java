package tw.waterballsa.challenge.features.auth.RefreshToken;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.ValidationException;
import tw.waterballsa.challenge.contexts.identity.domain.RefreshToken;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;
import tw.waterballsa.challenge.infrastructure.security.JwtUtil;
import tw.waterballsa.challenge.infrastructure.security.RefreshTokenService;

@Component
public class RefreshTokenHandler implements CommandHandler<RefreshTokenCommand, RefreshTokenResponse> {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public RefreshTokenHandler(RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public RefreshTokenResponse handle(RefreshTokenCommand command) {
        RefreshToken refreshToken = refreshTokenService.findByToken(command.getRefreshToken())
                .orElseThrow(() -> new ValidationException("Invalid refresh token"));

        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();

        String newAccessToken = jwtUtil.generateToken(user.getId(), user.getRole());
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        return new RefreshTokenResponse(newAccessToken, newRefreshToken.getToken());
    }
}
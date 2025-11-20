package tw.waterballsa.challenge.features.auth.SignIn;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.ValidationException;
import tw.waterballsa.challenge.contexts.identity.domain.RefreshToken;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;
import tw.waterballsa.challenge.features.auth.common.AuthResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;
import tw.waterballsa.challenge.infrastructure.security.JwtUtil;
import tw.waterballsa.challenge.infrastructure.security.RefreshTokenService;

@Component
public class SignInHandler implements CommandHandler<SignInCommand, AuthResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public SignInHandler(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthResponse handle(SignInCommand command) {
        // Find user by email
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new ValidationException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new ValidationException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getRole());

        // Generate refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                token,
                refreshToken.getToken(),
                user.getId(),
                user.getStudentId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getRole(),
                user.getRank()
        );
    }
}
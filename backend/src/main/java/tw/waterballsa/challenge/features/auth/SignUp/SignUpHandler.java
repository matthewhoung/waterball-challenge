package tw.waterballsa.challenge.features.auth.SignUp;

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
public class SignUpHandler implements CommandHandler<SignUpCommand, AuthResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public SignUpHandler(
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
    public AuthResponse handle(SignUpCommand command) {
        // Validate if email already exists
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new ValidationException("Email already registered");
        }

        // Extract username from email
        String username = extractUsernameFromEmail(command.getEmail());

        // Make username unique if needed
        if (userRepository.existsByUsername(username)) {
            username = makeUsernameUnique(username);
        }

        // Generate student ID based on current user count
        int studentId = (int) (userRepository.count() + 1);

        // Create new user
        User user = new User(
                username,
                command.getEmail(),
                passwordEncoder.encode(command.getPassword())
        );
        user.setStudentId(studentId);

        user = userRepository.save(user);

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

    private String extractUsernameFromEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new ValidationException("Invalid email format");
        }
        return email.substring(0, email.indexOf("@"));
    }

    private String makeUsernameUnique(String baseUsername) {
        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }

        return username;
    }
}
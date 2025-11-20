package tw.waterballsa.challenge.features.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.challenge.common.ApiResponse;
import tw.waterballsa.challenge.features.auth.GetUserDetails.GetUserDetailsQuery;
import tw.waterballsa.challenge.features.auth.GetUserDetails.UserDetailsResponse;
import tw.waterballsa.challenge.features.auth.RefreshToken.RefreshTokenCommand;
import tw.waterballsa.challenge.features.auth.RefreshToken.RefreshTokenRequest;
import tw.waterballsa.challenge.features.auth.RefreshToken.RefreshTokenResponse;
import tw.waterballsa.challenge.features.auth.SignIn.SignInCommand;
import tw.waterballsa.challenge.features.auth.SignIn.SignInRequest;
import tw.waterballsa.challenge.features.auth.SignUp.SignUpCommand;
import tw.waterballsa.challenge.features.auth.SignUp.SignUpRequest;
import tw.waterballsa.challenge.features.auth.common.AuthResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandBus;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryBus;
import tw.waterballsa.challenge.infrastructure.security.AuthenticationUtil;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public AuthController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<AuthResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpCommand command = new SignUpCommand(
                request.getEmail(),
                request.getPassword()
        );

        AuthResponse response = commandBus.execute(command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<AuthResponse>> signIn(@Valid @RequestBody SignInRequest request) {
        SignInCommand command = new SignInCommand(
                request.getEmail(),
                request.getPassword()
        );

        AuthResponse response = commandBus.execute(command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        RefreshTokenCommand command = new RefreshTokenCommand(request.getRefreshToken());
        RefreshTokenResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getCurrentUser() {
        UUID userId = AuthenticationUtil.getCurrentUserId();

        GetUserDetailsQuery query = new GetUserDetailsQuery(userId);
        UserDetailsResponse response = queryBus.execute(query);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
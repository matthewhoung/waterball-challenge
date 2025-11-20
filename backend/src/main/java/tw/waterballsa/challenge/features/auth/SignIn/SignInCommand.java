package tw.waterballsa.challenge.features.auth.SignIn;

import tw.waterballsa.challenge.features.auth.common.AuthResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

public class SignInCommand implements Command<AuthResponse> {
    private final String email;
    private final String password;

    public SignInCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
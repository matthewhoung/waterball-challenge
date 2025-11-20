package tw.waterballsa.challenge.features.auth.GetUserDetails;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

@Component
public class GetUserDetailsHandler implements QueryHandler<GetUserDetailsQuery, UserDetailsResponse> {

    private final UserRepository userRepository;

    public GetUserDetailsHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsResponse handle(GetUserDetailsQuery query) {
        User user = userRepository.findById(query.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        return new UserDetailsResponse(
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
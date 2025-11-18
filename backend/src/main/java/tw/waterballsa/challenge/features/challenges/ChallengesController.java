package tw.waterballsa.challenge.features.challenges;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.challenge.common.ApiResponse;
import tw.waterballsa.challenge.features.challenges.ListChallenges.ChallengeResponse;
import tw.waterballsa.challenge.features.challenges.ListChallenges.ListChallengesQuery;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryBus;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengesController {

    private final QueryBus queryBus;

    public ChallengesController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChallengeResponse>>> listChallenges() {
        var result = queryBus.execute(new ListChallengesQuery());
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
package tw.waterballsa.challenge.features.journey;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.challenge.common.ApiResponse;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;
import tw.waterballsa.challenge.features.journey.CreateJourney.CreateJourneyCommand;
import tw.waterballsa.challenge.features.journey.CreateJourney.CreateJourneyRequest;
import tw.waterballsa.challenge.features.journey.GetJourneyById.GetJourneyByIdQuery;
import tw.waterballsa.challenge.features.journey.GetJourneyById.GetJourneyByIdResponse;
import tw.waterballsa.challenge.features.journey.GetJourneys.GetJourneysQuery;
import tw.waterballsa.challenge.features.journey.GetJourneys.GetJourneysResponse;
import tw.waterballsa.challenge.features.journey.UpdateJourney.UpdateJourneyCommand;
import tw.waterballsa.challenge.features.journey.UpdateJourney.UpdateJourneyRequest;
import tw.waterballsa.challenge.features.journey.common.JourneyResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandBus;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryBus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public JourneyController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetJourneysResponse>>> getJourneys(
            @RequestParam(required = false) JourneyStatus status) {

        GetJourneysQuery query = new GetJourneysQuery(status);
        List<GetJourneysResponse> response = queryBus.execute(query);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetJourneyByIdResponse>> getJourneyById(
            @PathVariable UUID id) {

        GetJourneyByIdQuery query = new GetJourneyByIdQuery(id);
        GetJourneyByIdResponse response = queryBus.execute(query);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JourneyResponse>> createJourney(
            @Valid @RequestBody CreateJourneyRequest request) {

        CreateJourneyCommand command = new CreateJourneyCommand(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getLevel(),
                request.getEstimatedHours(),
                request.getDisplayOrder()
        );

        JourneyResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JourneyResponse>> updateJourney(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateJourneyRequest request) {

        UpdateJourneyCommand command = new UpdateJourneyCommand(
                id,
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getStatus(),
                request.getLevel(),
                request.getEstimatedHours(),
                request.getDisplayOrder()
        );

        JourneyResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
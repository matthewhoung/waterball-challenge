package tw.waterballsa.challenge.features.course;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.challenge.common.ApiResponse;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;
import tw.waterballsa.challenge.features.course.AddCourseToJourney.AddCourseToJourneyCommand;
import tw.waterballsa.challenge.features.course.CreateCourse.CreateCourseCommand;
import tw.waterballsa.challenge.features.course.CreateCourse.CreateCourseRequest;
import tw.waterballsa.challenge.features.course.GetCourseById.GetCourseByIdQuery;
import tw.waterballsa.challenge.features.course.GetCourseById.GetCourseByIdResponse;
import tw.waterballsa.challenge.features.course.GetCourses.GetCoursesQuery;
import tw.waterballsa.challenge.features.course.GetCourses.GetCoursesResponse;
import tw.waterballsa.challenge.features.course.UpdateCourse.UpdateCourseCommand;
import tw.waterballsa.challenge.features.course.UpdateCourse.UpdateCourseRequest;
import tw.waterballsa.challenge.features.course.common.CourseResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandBus;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryBus;
import tw.waterballsa.challenge.infrastructure.security.AuthenticationUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public CourseController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetCoursesResponse>>> getCourses(
            @RequestParam(required = false) UUID journeyId,
            @RequestParam(required = false) UUID instructorId,
            @RequestParam(required = false) CourseStatus status) {

        GetCoursesQuery query = new GetCoursesQuery(journeyId, instructorId, status);
        List<GetCoursesResponse> response = queryBus.execute(query);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetCourseByIdResponse>> getCourseById(@PathVariable UUID id) {
        GetCourseByIdQuery query = new GetCourseByIdQuery(id);
        GetCourseByIdResponse response = queryBus.execute(query);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MENTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @Valid @RequestBody CreateCourseRequest request) {

        UUID instructorId = AuthenticationUtil.getCurrentUserId();

        CourseLevel level = request.getLevel() != null ?
                CourseLevel.valueOf(request.getLevel().toUpperCase()) : null;

        CreateCourseCommand command = new CreateCourseCommand(
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                level,
                request.getEstimatedHours(),
                instructorId
        );

        CourseResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCourseRequest request) {

        UUID currentUserId = AuthenticationUtil.getCurrentUserId();

        UpdateCourseCommand command = new UpdateCourseCommand(
                id,
                currentUserId,
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getLevel(),
                request.getEstimatedHours(),
                request.getDisplayOrder(),
                request.getStatus()
        );

        CourseResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{courseId}/journeys/{journeyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> addCourseToJourney(
            @PathVariable UUID courseId,
            @PathVariable UUID journeyId) {

        AddCourseToJourneyCommand command = new AddCourseToJourneyCommand(journeyId, courseId);
        commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
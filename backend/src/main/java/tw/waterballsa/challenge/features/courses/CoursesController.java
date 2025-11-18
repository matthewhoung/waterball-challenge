package tw.waterballsa.challenge.features.courses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.challenge.common.ApiResponse;
import tw.waterballsa.challenge.features.courses.CreateCourse.CreateCourseCommand;
import tw.waterballsa.challenge.features.courses.GetCourseDetail.CourseDetailResponse;
import tw.waterballsa.challenge.features.courses.GetCourseDetail.GetCourseDetailQuery;
import tw.waterballsa.challenge.features.courses.ListCourses.CourseResponse;
import tw.waterballsa.challenge.features.courses.ListCourses.ListCoursesQuery;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandBus;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryBus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public CoursesController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> listCourses(
            @RequestParam(defaultValue = "true") boolean publishedOnly
    ) {
        var result = queryBus.execute(new ListCoursesQuery(publishedOnly));
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseDetailResponse>> getCourseDetail(
            @PathVariable UUID courseId
    ) {
        var result = queryBus.execute(new GetCourseDetailQuery(courseId));
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UUID>> createCourse(@RequestBody CreateCourseCommand command) {
        var courseId = commandBus.execute(command);
        return ResponseEntity.ok(ApiResponse.success(courseId));
    }
}
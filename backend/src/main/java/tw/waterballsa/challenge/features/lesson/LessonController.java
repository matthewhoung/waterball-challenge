package tw.waterballsa.challenge.features.lesson;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.waterballsa.challenge.common.ApiResponse;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonStatus;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;
import tw.waterballsa.challenge.features.lesson.CreateLesson.CreateLessonCommand;
import tw.waterballsa.challenge.features.lesson.CreateLesson.CreateLessonRequest;
import tw.waterballsa.challenge.features.lesson.GetLessonById.GetLessonByIdQuery;
import tw.waterballsa.challenge.features.lesson.GetLessons.GetLessonsQuery;
import tw.waterballsa.challenge.features.lesson.GetLessons.GetLessonsResponse;
import tw.waterballsa.challenge.features.lesson.UpdateLesson.UpdateLessonCommand;
import tw.waterballsa.challenge.features.lesson.UpdateLesson.UpdateLessonRequest;
import tw.waterballsa.challenge.features.lesson.UploadVideo.UploadVideoCommand;
import tw.waterballsa.challenge.features.lesson.UploadVideo.UploadVideoResponse;
import tw.waterballsa.challenge.features.lesson.common.LessonResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandBus;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryBus;
import tw.waterballsa.challenge.infrastructure.security.AuthenticationUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public LessonController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetLessonsResponse>>> getLessonsByCourse(
            @RequestParam UUID courseId) {

        GetLessonsQuery query = new GetLessonsQuery(courseId);
        List<GetLessonsResponse> response = queryBus.execute(query);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponse>> getLessonById(@PathVariable UUID id) {
        GetLessonByIdQuery query = new GetLessonByIdQuery(id);
        LessonResponse response = queryBus.execute(query);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MENTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponse>> createLesson(
            @Valid @RequestBody CreateLessonRequest request) {

        UUID currentUserId = AuthenticationUtil.getCurrentUserId();

        CreateLessonCommand command = new CreateLessonCommand(
                UUID.fromString(request.getCourseId()),
                currentUserId,
                request.getTitle(),
                request.getDescription(),
                LessonType.valueOf(request.getLessonType().toUpperCase()),
                request.getDisplayOrder(),
                request.getEstimatedMinutes(),
                request.getArticleContent(),
                request.getVideoDuration()
        );

        LessonResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLesson(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLessonRequest request) {

        UUID currentUserId = AuthenticationUtil.getCurrentUserId();

        LessonStatus status = request.getStatus() != null ?
                LessonStatus.valueOf(request.getStatus().toUpperCase()) : null;

        UpdateLessonCommand command = new UpdateLessonCommand(
                id,
                currentUserId,
                request.getTitle(),
                request.getDescription(),
                request.getDisplayOrder(),
                request.getEstimatedMinutes(),
                status,
                request.getArticleContent()
        );

        LessonResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/upload-video")
    @PreAuthorize("hasAnyRole('MENTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<UploadVideoResponse>> uploadVideo(
            @PathVariable UUID id,
            @RequestParam("video") MultipartFile videoFile,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnailFile) {

        UUID currentUserId = AuthenticationUtil.getCurrentUserId();

        UploadVideoCommand command = new UploadVideoCommand(
                id,
                currentUserId,
                videoFile,
                thumbnailFile
        );

        UploadVideoResponse response = commandBus.execute(command);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
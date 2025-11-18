package tw.waterballsa.challenge.features.lessons;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterballsa.challenge.common.ApiResponse;
import tw.waterballsa.challenge.features.lessons.CreateLesson.CreateLessonCommand;
import tw.waterballsa.challenge.features.lessons.SubmitLesson.SubmitLessonCommand;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandBus;

import java.util.UUID;

@RestController
@RequestMapping("/api/lessons")
public class LessonsController {

    private final CommandBus commandBus;

    public LessonsController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UUID>> createLesson(@RequestBody CreateLessonCommand command) {
        var lessonId = commandBus.execute(command);
        return ResponseEntity.ok(ApiResponse.success(lessonId));
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<Void>> submitLesson(@RequestBody SubmitLessonCommand command) {
        commandBus.execute(command);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
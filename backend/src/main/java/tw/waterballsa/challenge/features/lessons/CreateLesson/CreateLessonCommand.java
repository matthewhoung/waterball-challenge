package tw.waterballsa.challenge.features.lessons.CreateLesson;

import tw.waterballsa.challenge.infrastructure.cqrs.Command;
import java.util.UUID;

public record CreateLessonCommand(
        UUID courseId,
        String title,
        String description,
        boolean isChallenge,
        String videoUrl,
        Integer durationSeconds,
        String articleContent,
        String prerequisites,
        UUID prerequisiteLessonId,
        Integer timeLimitDays,
        Integer orderIndex
) implements Command<UUID> {}
package tw.waterballsa.challenge.features.lessons.SubmitLesson;

import tw.waterballsa.challenge.contexts.content.domain.enums.SubmissionType;
import tw.waterballsa.challenge.infrastructure.cqrs.Command;

import java.util.List;
import java.util.UUID;

public record SubmitLessonCommand(
        UUID studentId,
        UUID lessonId,
        List<SubmissionFile> files
) implements Command<Void> {
    public record SubmissionFile(
            SubmissionType submissionType,
            String fileUrl,
            String fileName
    ) {}
}
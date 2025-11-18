package tw.waterballsa.challenge.features.lessons.SubmitLesson;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.content.domain.Submission;
import tw.waterballsa.challenge.contexts.content.repository.LessonRepository;
import tw.waterballsa.challenge.contexts.content.repository.SubmissionRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class SubmitLessonHandler implements CommandHandler<SubmitLessonCommand, Void> {

    private final LessonRepository lessonRepository;
    private final SubmissionRepository submissionRepository;

    public SubmitLessonHandler(LessonRepository lessonRepository, SubmissionRepository submissionRepository) {
        this.lessonRepository = lessonRepository;
        this.submissionRepository = submissionRepository;
    }

    @Override
    @Transactional
    public Void handle(SubmitLessonCommand command) {
        var lesson = lessonRepository.findById(command.lessonId())
                .orElseThrow(() -> new NotFoundException("Lesson", command.lessonId()));

        if (!lesson.isChallenge()) {
            throw new IllegalStateException("Cannot submit to non-challenge lesson");
        }

        var submissions = command.files().stream()
                .map(f -> {
                    var submission = new Submission(
                            command.studentId(),
                            command.lessonId(),
                            f.submissionType(),
                            f.fileUrl()
                    );
                    submission.setFileName(f.fileName());
                    return submission;
                })
                .toList();

        submissionRepository.saveAll(submissions);
        return null;
    }
}
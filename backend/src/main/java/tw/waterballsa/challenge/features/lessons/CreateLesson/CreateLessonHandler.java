package tw.waterballsa.challenge.features.lessons.CreateLesson;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.contexts.content.domain.Lesson;
import tw.waterballsa.challenge.contexts.content.repository.LessonRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

import java.util.UUID;

@Component
public class CreateLessonHandler implements CommandHandler<CreateLessonCommand, UUID> {

    private final LessonRepository lessonRepository;

    public CreateLessonHandler(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public UUID handle(CreateLessonCommand command) {
        var lesson = new Lesson(command.courseId(), command.title(), command.isChallenge());
        lesson.setDescription(command.description());
        lesson.setVideoUrl(command.videoUrl());
        lesson.setDurationSeconds(command.durationSeconds());
        lesson.setArticleContent(command.articleContent());
        lesson.setPrerequisites(command.prerequisites());
        lesson.setPrerequisiteLessonId(command.prerequisiteLessonId());
        lesson.setTimeLimitDays(command.timeLimitDays());
        lesson.setOrderIndex(command.orderIndex());

        lessonRepository.save(lesson);
        return lesson.getId();
    }
}
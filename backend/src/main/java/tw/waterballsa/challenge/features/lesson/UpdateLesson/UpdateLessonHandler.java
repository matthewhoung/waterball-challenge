package tw.waterballsa.challenge.features.lesson.UpdateLesson;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.waterballsa.challenge.common.exceptions.BusinessException;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;
import tw.waterballsa.challenge.contexts.lesson.domain.Lesson;
import tw.waterballsa.challenge.contexts.lesson.repository.LessonRepository;
import tw.waterballsa.challenge.features.lesson.common.LessonResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class UpdateLessonHandler implements CommandHandler<UpdateLessonCommand, LessonResponse> {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public UpdateLessonHandler(LessonRepository lessonRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public LessonResponse handle(UpdateLessonCommand command) {
        Lesson lesson = lessonRepository.findByIdWithContent(command.getLessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        User currentUser = userRepository.findById(command.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check if user is instructor or admin
        if (!lesson.getCourse().getInstructor().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != Roles.ADMIN) {
            throw new BusinessException("You don't have permission to update this lesson");
        }

        if (command.getTitle() != null) {
            lesson.setTitle(command.getTitle());
        }
        if (command.getDescription() != null) {
            lesson.setDescription(command.getDescription());
        }
        if (command.getDisplayOrder() != null) {
            lesson.setDisplayOrder(command.getDisplayOrder());
        }
        if (command.getEstimatedMinutes() != null) {
            lesson.setEstimatedMinutes(command.getEstimatedMinutes());
        }
        if (command.getStatus() != null) {
            lesson.setStatus(command.getStatus());
        }
        if (command.getArticleContent() != null && lesson.getArticle() != null) {
            lesson.getArticle().setContent(command.getArticleContent());
        }

        lesson = lessonRepository.save(lesson);

        return mapToResponse(lesson);
    }

    private LessonResponse mapToResponse(Lesson lesson) {
        LessonResponse response = new LessonResponse();
        response.setId(lesson.getId());
        response.setCourseId(lesson.getCourse().getId());
        response.setTitle(lesson.getTitle());
        response.setDescription(lesson.getDescription());
        response.setLessonType(lesson.getLessonType());
        response.setDisplayOrder(lesson.getDisplayOrder());
        response.setEstimatedMinutes(lesson.getEstimatedMinutes());
        response.setStatus(lesson.getStatus());

        if (lesson.getArticle() != null) {
            response.setArticle(new LessonResponse.ArticleContent(
                    lesson.getArticle().getId(),
                    lesson.getArticle().getContent(),
                    lesson.getArticle().getReadingTimeMinutes()
            ));
        }

        if (lesson.getVideo() != null) {
            response.setVideo(new LessonResponse.VideoContent(
                    lesson.getVideo().getId(),
                    lesson.getVideo().getVideoUrl(),
                    lesson.getVideo().getThumbnailUrl(),
                    lesson.getVideo().getDuration(),
                    lesson.getVideo().getResolution()
            ));
        }

        return response;
    }
}
package tw.waterballsa.challenge.features.lesson.CreateLesson;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.BusinessException;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.common.exceptions.ValidationException;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.course.repository.CourseRepository;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;
import tw.waterballsa.challenge.contexts.lesson.domain.Article;
import tw.waterballsa.challenge.contexts.lesson.domain.Lesson;
import tw.waterballsa.challenge.contexts.lesson.domain.Video;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonStatus;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;
import tw.waterballsa.challenge.contexts.lesson.repository.LessonRepository;
import tw.waterballsa.challenge.features.lesson.common.LessonResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.CommandHandler;

@Component
public class CreateLessonHandler implements CommandHandler<CreateLessonCommand, LessonResponse> {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CreateLessonHandler(LessonRepository lessonRepository, CourseRepository courseRepository,
                               UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LessonResponse handle(CreateLessonCommand command) {
        Course course = courseRepository.findById(command.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        User currentUser = userRepository.findById(command.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check if user is instructor or admin
        if (!course.getInstructor().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != Roles.ADMIN) {
            throw new BusinessException("You don't have permission to add lessons to this course");
        }

        Lesson lesson = new Lesson(command.getTitle(), command.getLessonType(), course);
        lesson.setDescription(command.getDescription());
        lesson.setDisplayOrder(command.getDisplayOrder() != null ? command.getDisplayOrder() : 0);
        lesson.setEstimatedMinutes(command.getEstimatedMinutes());
        lesson.setStatus(LessonStatus.DRAFT);

        // Create article content if type is ARTICLE or MIXED
        if (command.getLessonType() == LessonType.ARTICLE || command.getLessonType() == LessonType.MIXED) {
            if (command.getArticleContent() == null || command.getArticleContent().isBlank()) {
                throw new ValidationException("Article content is required for ARTICLE or MIXED lesson type");
            }
            Article article = new Article(lesson, command.getArticleContent());
            lesson.setArticle(article);
        }

        // Create video placeholder if type is VIDEO or MIXED
        if (command.getLessonType() == LessonType.VIDEO || command.getLessonType() == LessonType.MIXED) {
            if (command.getVideoDuration() == null || command.getVideoDuration() <= 0) {
                throw new ValidationException("Video duration is required for VIDEO or MIXED lesson type");
            }
            // Video URL will be set when video is uploaded
            Video video = new Video(lesson, "pending-upload", command.getVideoDuration());
            lesson.setVideo(video);
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
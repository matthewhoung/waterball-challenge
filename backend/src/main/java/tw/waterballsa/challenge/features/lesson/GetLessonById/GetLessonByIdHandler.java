package tw.waterballsa.challenge.features.lesson.GetLessonById;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.common.exceptions.NotFoundException;
import tw.waterballsa.challenge.contexts.lesson.domain.Lesson;
import tw.waterballsa.challenge.contexts.lesson.repository.LessonRepository;
import tw.waterballsa.challenge.features.lesson.common.LessonResponse;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

@Component
public class GetLessonByIdHandler implements QueryHandler<GetLessonByIdQuery, LessonResponse> {

    private final LessonRepository lessonRepository;

    public GetLessonByIdHandler(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public LessonResponse handle(GetLessonByIdQuery query) {
        Lesson lesson = lessonRepository.findByIdWithContent(query.getLessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

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
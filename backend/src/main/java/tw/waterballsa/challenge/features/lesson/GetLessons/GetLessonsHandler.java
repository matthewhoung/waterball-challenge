package tw.waterballsa.challenge.features.lesson.GetLessons;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.contexts.lesson.domain.Lesson;
import tw.waterballsa.challenge.contexts.lesson.repository.LessonRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetLessonsHandler implements QueryHandler<GetLessonsQuery, List<GetLessonsResponse>> {

    private final LessonRepository lessonRepository;

    public GetLessonsHandler(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<GetLessonsResponse> handle(GetLessonsQuery query) {
        List<Lesson> lessons = lessonRepository.findByCourseIdOrderByDisplayOrderAsc(query.getCourseId());

        return lessons.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private GetLessonsResponse mapToResponse(Lesson lesson) {
        return new GetLessonsResponse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getLessonType(),
                lesson.getDisplayOrder(),
                lesson.getEstimatedMinutes(),
                lesson.getStatus(),
                lesson.getArticle() != null,
                lesson.getVideo() != null
        );
    }
}
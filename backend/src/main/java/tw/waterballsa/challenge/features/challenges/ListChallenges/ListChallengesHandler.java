package tw.waterballsa.challenge.features.challenges.ListChallenges;

import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.contexts.content.repository.LessonRepository;
import tw.waterballsa.challenge.infrastructure.cqrs.QueryHandler;

import java.util.List;

@Component
public class ListChallengesHandler implements QueryHandler<ListChallengesQuery, List<ChallengeResponse>> {

    private final LessonRepository lessonRepository;

    public ListChallengesHandler(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<ChallengeResponse> handle(ListChallengesQuery query) {
        var challenges = lessonRepository.findByCourseIdIsNullAndIsChallengeTrue();

        return challenges.stream()
                .map(c -> new ChallengeResponse(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getPrerequisites(),
                        c.getTimeLimitDays()
                ))
                .toList();
    }
}
package tw.waterballsa.challenge.features.courses.GetCourseDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CourseDetailResponse(
        UUID id,
        String title,
        String description,
        String thumbnailUrl,
        BigDecimal price,
        boolean purchaseRequired,
        List<LessonSummary> lessons
) {
    public record LessonSummary(
            UUID id,
            String title,
            boolean isChallenge,
            Integer orderIndex,
            boolean preview
    ) {}
}
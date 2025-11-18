package tw.waterballsa.challenge.features.courses.ListCourses;

import java.math.BigDecimal;
import java.util.UUID;

public record CourseResponse(
        UUID id,
        String title,
        String description,
        String thumbnailUrl,
        BigDecimal price,
        boolean purchaseRequired,
        boolean introCourse,
        BigDecimal discountPercentage
) {}
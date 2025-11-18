package tw.waterballsa.challenge.features.courses.CreateCourse;

import tw.waterballsa.challenge.infrastructure.cqrs.Command;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateCourseCommand(
        String title,
        String description,
        BigDecimal price,
        boolean purchaseRequired,
        boolean introCourse,
        BigDecimal discountPercentage
) implements Command<UUID> {}
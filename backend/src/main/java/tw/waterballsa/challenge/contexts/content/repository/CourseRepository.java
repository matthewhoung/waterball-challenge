package tw.waterballsa.challenge.contexts.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.waterballsa.challenge.contexts.content.domain.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByPublishedTrue();

    Optional<Course> findByIntroCourseTrue();
}

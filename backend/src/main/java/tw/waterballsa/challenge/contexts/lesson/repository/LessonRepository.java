package tw.waterballsa.challenge.contexts.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.lesson.domain.Lesson;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    List<Lesson> findByCourseIdOrderByDisplayOrderAsc(UUID courseId);

    List<Lesson> findByCourseIdAndStatusOrderByDisplayOrderAsc(UUID courseId, LessonStatus status);

    @Query("SELECT l FROM Lesson l LEFT JOIN FETCH l.video LEFT JOIN FETCH l.article WHERE l.id = :id")
    Optional<Lesson> findByIdWithContent(@Param("id") UUID id);

    long countByCourseId(UUID courseId);
}
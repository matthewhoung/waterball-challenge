package tw.waterballsa.challenge.contexts.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.lesson.domain.LessonProgress;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, UUID> {

    Optional<LessonProgress> findByUserIdAndLessonId(UUID userId, UUID lessonId);

    List<LessonProgress> findByUserId(UUID userId);

    @Query("SELECT lp FROM LessonProgress lp WHERE lp.user.id = :userId AND lp.lesson.course.id = :courseId")
    List<LessonProgress> findByUserIdAndCourseId(@Param("userId") UUID userId, @Param("courseId") UUID courseId);

    @Query("SELECT COUNT(lp) FROM LessonProgress lp WHERE lp.lesson.course.id = :courseId AND lp.completed = true AND lp.user.id = :userId")
    long countCompletedLessonsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);
}
package tw.waterballsa.challenge.contexts.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.content.domain.Lesson;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    List<Lesson> findByCourseIdOrderByOrderIndexAsc(UUID courseId);

    List<Lesson> findByCourseIdIsNullAndIsChallengeTrue();

    List<Lesson> findByPublishedTrue();

    List<Lesson> findByCourseIdAndPreviewTrue(UUID courseId);
}
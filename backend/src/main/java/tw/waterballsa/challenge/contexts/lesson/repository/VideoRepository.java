package tw.waterballsa.challenge.contexts.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.lesson.domain.Video;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.VideoStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {

    Optional<Video> findByLessonId(UUID lessonId);

    List<Video> findByStatus(VideoStatus status);
}
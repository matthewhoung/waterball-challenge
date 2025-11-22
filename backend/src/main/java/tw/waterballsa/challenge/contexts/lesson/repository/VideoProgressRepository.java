package tw.waterballsa.challenge.contexts.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.lesson.domain.VideoProgress;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideoProgressRepository extends JpaRepository<VideoProgress, UUID> {

    Optional<VideoProgress> findByUserIdAndVideoId(UUID userId, UUID videoId);
}
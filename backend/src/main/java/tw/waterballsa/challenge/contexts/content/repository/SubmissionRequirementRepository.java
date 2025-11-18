package tw.waterballsa.challenge.contexts.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.content.domain.SubmissionRequirement;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRequirementRepository extends JpaRepository<SubmissionRequirement, UUID> {

    List<SubmissionRequirement> findByLessonIdOrderByOrderIndexAsc(UUID lessonId);

    void deleteByLessonId(UUID lessonId);
}

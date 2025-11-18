package tw.waterballsa.challenge.contexts.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.content.domain.Submission;
import tw.waterballsa.challenge.contexts.content.domain.enums.SubmissionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {

    List<Submission> findByStudentIdAndLessonId(UUID studentId, UUID lessonId);

    Optional<Submission> findByStudentIdAndLessonIdAndSubmissionType(
            UUID studentId, UUID lessonId, SubmissionType submissionType);

    List<Submission> findByStudentId(UUID studentId);

    long countByStudentIdAndLessonId(UUID studentId, UUID lessonId);
}

package tw.waterballsa.challenge.contexts.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findByInstructorIdAndStatus(UUID instructorId, CourseStatus status);

    List<Course> findByStatusOrderByDisplayOrderAsc(CourseStatus status);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.lessons WHERE c.id = :id")
    Optional<Course> findByIdWithLessons(@Param("id") UUID id);

    @Query("SELECT c FROM Course c JOIN c.journeys j WHERE j.id = :journeyId ORDER BY c.displayOrder ASC")
    List<Course> findByJourneyId(@Param("journeyId") UUID journeyId);

    boolean existsByTitle(String title);
}
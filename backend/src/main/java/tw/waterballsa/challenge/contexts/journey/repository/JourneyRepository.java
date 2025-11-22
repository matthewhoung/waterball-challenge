package tw.waterballsa.challenge.contexts.journey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.journey.domain.Journey;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, UUID> {

    List<Journey> findByStatusOrderByDisplayOrderAsc(JourneyStatus status);

    @Query("SELECT j FROM Journey j LEFT JOIN FETCH j.courses WHERE j.id = :id")
    Journey findByIdWithCourses(@Param("id") UUID id);

    boolean existsByTitle(String title);
}
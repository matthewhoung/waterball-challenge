package tw.waterballsa.challenge.contexts.journey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.journey.domain.Journey;
import tw.waterballsa.challenge.contexts.journey.domain.JourneyEnrollment;
import tw.waterballsa.challenge.contexts.journey.domain.enums.EnrollmentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JourneyEnrollmentRepository extends JpaRepository<JourneyEnrollment, UUID> {

    Optional<JourneyEnrollment> findByUserAndJourney(User user, Journey journey);

    @Query("SELECT je FROM JourneyEnrollment je WHERE je.user.id = :userId AND je.journey.id = :journeyId")
    Optional<JourneyEnrollment> findByUserIdAndJourneyId(@Param("userId") UUID userId, @Param("journeyId") UUID journeyId);

    List<JourneyEnrollment> findByUserAndStatus(User user, EnrollmentStatus status);

    @Query("SELECT je FROM JourneyEnrollment je JOIN FETCH je.journey WHERE je.user.id = :userId")
    List<JourneyEnrollment> findByUserIdWithJourney(@Param("userId") UUID userId);

    boolean existsByUserAndJourney(User user, Journey journey);
}
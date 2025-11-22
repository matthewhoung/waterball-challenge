package tw.waterballsa.challenge.contexts.journey.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.journey.domain.enums.EnrollmentStatus;

import java.time.Instant;

@Entity
@Table(
        name = "journey_enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "journey_id"})
)
public class JourneyEnrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journey_id", nullable = false)
    private Journey journey;

    @Column(nullable = false)
    private Instant enrolledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(nullable = false)
    private Integer overallProgress = 0;

    private Instant expiresAt;

    public JourneyEnrollment() {}

    public JourneyEnrollment(User user, Journey journey) {
        this.user = user;
        this.journey = journey;
        this.enrolledAt = Instant.now();
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public Instant getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(Instant enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public Integer getOverallProgress() {
        return overallProgress;
    }

    public void setOverallProgress(Integer overallProgress) {
        this.overallProgress = overallProgress;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return status == EnrollmentStatus.ACTIVE &&
                (expiresAt == null || Instant.now().isBefore(expiresAt));
    }
}
package tw.waterballsa.challenge.contexts.lesson.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.identity.domain.User;

import java.time.Instant;

@Entity
@Table(
        name = "lesson_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"})
)
public class LessonProgress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private Boolean completed = false;

    private Instant completedAt;

    private Instant lastAccessedAt;

    public LessonProgress() {}

    public LessonProgress(User user, Lesson lesson) {
        this.user = user;
        this.lesson = lesson;
        this.lastAccessedAt = Instant.now();
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
        if (completed && this.completedAt == null) {
            this.completedAt = Instant.now();
        }
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Instant getLastAccessedAt() {
        return lastAccessedAt;
    }

    public void setLastAccessedAt(Instant lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    public void markAsCompleted() {
        this.completed = true;
        this.completedAt = Instant.now();
    }

    public void updateLastAccessed() {
        this.lastAccessedAt = Instant.now();
    }
}
package tw.waterballsa.challenge.contexts.lesson.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.identity.domain.User;

import java.time.Instant;

@Entity
@Table(
        name = "video_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "video_id"})
)
public class VideoProgress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_progress_id")
    private LessonProgress lessonProgress;

    @Column(nullable = false)
    private Integer lastWatchedSecond = 0;

    @Column(nullable = false)
    private Integer totalWatchedSeconds = 0;

    @Column(nullable = false)
    private Integer completionPercentage = 0;

    @Column(nullable = false)
    private Boolean completed = false;

    private Instant lastUpdatedAt;

    public VideoProgress() {}

    public VideoProgress(User user, Video video, LessonProgress lessonProgress) {
        this.user = user;
        this.video = video;
        this.lessonProgress = lessonProgress;
        this.lastUpdatedAt = Instant.now();
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public LessonProgress getLessonProgress() {
        return lessonProgress;
    }

    public void setLessonProgress(LessonProgress lessonProgress) {
        this.lessonProgress = lessonProgress;
    }

    public Integer getLastWatchedSecond() {
        return lastWatchedSecond;
    }

    public void setLastWatchedSecond(Integer lastWatchedSecond) {
        this.lastWatchedSecond = lastWatchedSecond;
    }

    public Integer getTotalWatchedSeconds() {
        return totalWatchedSeconds;
    }

    public void setTotalWatchedSeconds(Integer totalWatchedSeconds) {
        this.totalWatchedSeconds = totalWatchedSeconds;
    }

    public Integer getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Integer completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public void updateProgress(Integer currentSecond, Integer videoDuration) {
        this.lastWatchedSecond = currentSecond;
        this.completionPercentage = (currentSecond * 100) / videoDuration;
        this.completed = this.completionPercentage >= 95;
        this.lastUpdatedAt = Instant.now();
    }
}
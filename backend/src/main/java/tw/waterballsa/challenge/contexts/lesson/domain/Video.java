package tw.waterballsa.challenge.contexts.lesson.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.VideoStatus;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "videos")
public class Video extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false, unique = true)
    private Lesson lesson;

    @Column(nullable = false)
    private String videoUrl;

    private String thumbnailUrl;

    @Column(nullable = false)
    private Integer duration; // in seconds

    private String resolution;

    private Long fileSize; // in bytes

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoStatus status = VideoStatus.UPLOADING;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoProgress> progressRecords = new HashSet<>();

    public Video() {}

    public Video(Lesson lesson, String videoUrl, Integer duration) {
        this.lesson = lesson;
        this.videoUrl = videoUrl;
        this.duration = duration;
    }

    // Getters and Setters
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(VideoStatus status) {
        this.status = status;
    }

    public Set<VideoProgress> getProgressRecords() {
        return progressRecords;
    }

    public void setProgressRecords(Set<VideoProgress> progressRecords) {
        this.progressRecords = progressRecords;
    }
}
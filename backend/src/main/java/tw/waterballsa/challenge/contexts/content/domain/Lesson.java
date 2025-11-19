package tw.waterballsa.challenge.contexts.content.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;

import java.util.UUID;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    @Column(name = "course_id")
    private UUID courseId;

    @Column(nullable = false)
    private boolean isChallenge = false;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Integer orderIndex = 0;

    // Content
    private String videoUrl;

    @Column(length = 10000)
    private String articleContent;

    // Prerequisites
    @Column(length = 1000)
    private String prerequisites;

    @Column(name = "prerequisite_lesson_id")
    private UUID prerequisiteLessonId;

    // Challenge-specific
    private Integer timeLimitDays;

    // Settings
    private boolean preview = false;
    private boolean published = false;

    // Constructors
    public Lesson() {}

    public Lesson(UUID courseId, String title, boolean isChallenge) {
        this.courseId = courseId;
        this.title = title;
        this.isChallenge = isChallenge;
    }

    // Getters and Setters
    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public boolean isChallenge() {
        return isChallenge;
    }

    public void setChallenge(boolean challenge) {
        isChallenge = challenge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public UUID getPrerequisiteLessonId() {
        return prerequisiteLessonId;
    }

    public void setPrerequisiteLessonId(UUID prerequisiteLessonId) {
        this.prerequisiteLessonId = prerequisiteLessonId;
    }

    public Integer getTimeLimitDays() {
        return timeLimitDays;
    }

    public void setTimeLimitDays(Integer timeLimitDays) {
        this.timeLimitDays = timeLimitDays;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
package tw.waterballsa.challenge.features.lesson.common;

import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonStatus;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;

import java.util.UUID;

public class LessonResponse {
    private UUID id;
    private UUID courseId;
    private String title;
    private String description;
    private LessonType lessonType;
    private Integer displayOrder;
    private Integer estimatedMinutes;
    private LessonStatus status;
    private ArticleContent article;
    private VideoContent video;

    public static class ArticleContent {
        private UUID id;
        private String content;
        private Integer readingTimeMinutes;

        public ArticleContent() {}

        public ArticleContent(UUID id, String content, Integer readingTimeMinutes) {
            this.id = id;
            this.content = content;
            this.readingTimeMinutes = readingTimeMinutes;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public Integer getReadingTimeMinutes() { return readingTimeMinutes; }
        public void setReadingTimeMinutes(Integer readingTimeMinutes) { this.readingTimeMinutes = readingTimeMinutes; }
    }

    public static class VideoContent {
        private UUID id;
        private String videoUrl;
        private String thumbnailUrl;
        private Integer duration;
        private String resolution;

        public VideoContent() {}

        public VideoContent(UUID id, String videoUrl, String thumbnailUrl, Integer duration, String resolution) {
            this.id = id;
            this.videoUrl = videoUrl;
            this.thumbnailUrl = thumbnailUrl;
            this.duration = duration;
            this.resolution = resolution;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getVideoUrl() { return videoUrl; }
        public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
        public String getResolution() { return resolution; }
        public void setResolution(String resolution) { this.resolution = resolution; }
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LessonType getLessonType() { return lessonType; }
    public void setLessonType(LessonType lessonType) { this.lessonType = lessonType; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }
    public LessonStatus getStatus() { return status; }
    public void setStatus(LessonStatus status) { this.status = status; }
    public ArticleContent getArticle() { return article; }
    public void setArticle(ArticleContent article) { this.article = article; }
    public VideoContent getVideo() { return video; }
    public void setVideo(VideoContent video) { this.video = video; }
}
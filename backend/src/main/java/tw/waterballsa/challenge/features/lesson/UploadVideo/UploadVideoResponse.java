package tw.waterballsa.challenge.features.lesson.UploadVideo;

import java.util.UUID;

public class UploadVideoResponse {
    private UUID videoId;
    private String videoUrl;
    private String thumbnailUrl;
    private String message;

    public UploadVideoResponse() {}

    public UploadVideoResponse(UUID videoId, String videoUrl, String thumbnailUrl, String message) {
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.message = message;
    }

    // Getters and Setters
    public UUID getVideoId() { return videoId; }
    public void setVideoId(UUID videoId) { this.videoId = videoId; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
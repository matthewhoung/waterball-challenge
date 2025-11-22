package tw.waterballsa.challenge.contexts.lesson.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false, unique = true)
    private Lesson lesson;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Integer readingTimeMinutes;

    public Article() {}

    public Article(Lesson lesson, String content) {
        this.lesson = lesson;
        this.content = content;
    }

    // Getters and Setters
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReadingTimeMinutes() {
        return readingTimeMinutes;
    }

    public void setReadingTimeMinutes(Integer readingTimeMinutes) {
        this.readingTimeMinutes = readingTimeMinutes;
    }
}
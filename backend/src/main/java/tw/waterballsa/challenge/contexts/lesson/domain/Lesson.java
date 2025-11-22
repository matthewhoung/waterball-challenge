package tw.waterballsa.challenge.contexts.lesson.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonStatus;
import tw.waterballsa.challenge.contexts.lesson.domain.enums.LessonType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonType lessonType;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    private Integer estimatedMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonStatus status = LessonStatus.DRAFT;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Article article;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Video video;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonProgress> progressRecords = new HashSet<>();

    public Lesson() {}

    public Lesson(String title, LessonType lessonType, Course course) {
        this.title = title;
        this.lessonType = lessonType;
        this.course = course;
    }

    // Getters and Setters
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(Integer estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
        if (article != null) {
            article.setLesson(this);
        }
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
        if (video != null) {
            video.setLesson(this);
        }
    }

    public Set<LessonProgress> getProgressRecords() {
        return progressRecords;
    }

    public void setProgressRecords(Set<LessonProgress> progressRecords) {
        this.progressRecords = progressRecords;
    }
}
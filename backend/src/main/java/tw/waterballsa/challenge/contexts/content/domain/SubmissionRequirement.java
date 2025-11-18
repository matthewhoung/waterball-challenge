package tw.waterballsa.challenge.contexts.content.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.content.domain.enums.SubmissionType;

import java.util.UUID;

@Entity
@Table(name = "submission_requirements")
public class SubmissionRequirement extends BaseEntity {

    @Column(name = "lesson_id", nullable = false)
    private UUID lessonId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionType submissionType;

    @Column(nullable = false)
    private String label;

    @Column(length = 500)
    private String description;

    private boolean required = true;

    @Column(nullable = false)
    private Integer orderIndex = 0;

    // Constructors
    public SubmissionRequirement() {}

    public SubmissionRequirement(UUID lessonId, SubmissionType submissionType, String label) {
        this.lessonId = lessonId;
        this.submissionType = submissionType;
        this.label = label;
    }

    // Getters and Setters
    public UUID getLessonId() {
        return lessonId;
    }

    public void setLessonId(UUID lessonId) {
        this.lessonId = lessonId;
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(SubmissionType submissionType) {
        this.submissionType = submissionType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
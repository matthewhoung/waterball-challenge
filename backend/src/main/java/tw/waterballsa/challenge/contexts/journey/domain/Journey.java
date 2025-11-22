package tw.waterballsa.challenge.contexts.journey.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.course.domain.Course;
import tw.waterballsa.challenge.contexts.journey.domain.enums.JourneyStatus;
import tw.waterballsa.challenge.contexts.course.domain.enums.CourseLevel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "journeys")
public class Journey extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String coverImage;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyStatus status = JourneyStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    private Integer estimatedHours;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    @ManyToMany
    @JoinTable(
            name = "journey_courses",
            joinColumns = @JoinColumn(name = "journey_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JourneyEnrollment> enrollments = new HashSet<>();

    public Journey() {}

    public Journey(String title, String description, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public JourneyStatus getStatus() {
        return status;
    }

    public void setStatus(JourneyStatus status) {
        this.status = status;
    }

    public CourseLevel getLevel() {
        return level;
    }

    public void setLevel(CourseLevel level) {
        this.level = level;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<JourneyEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<JourneyEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getJourneys().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getJourneys().remove(this);
    }
}
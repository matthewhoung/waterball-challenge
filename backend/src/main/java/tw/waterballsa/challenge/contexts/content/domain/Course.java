package tw.waterballsa.challenge.contexts.content.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(nullable = false)
    private String title;  // e.g., "軟體設計模式精通之旅"

    @Column(length = 2000)
    private String description;

    private String thumbnailUrl;

    // Purchase settings
    private boolean purchaseRequired = true;
    private BigDecimal price;

    // Intro course settings (for discount eligibility)
    private boolean introCourse = false;
    private BigDecimal discountPercentage;  // e.g., 10.00 for 10%

    private boolean published = false;

    // Constructors
    public Course() {}

    public Course(String title, String description) {
        this.title = title;
        this.description = description;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isPurchaseRequired() {
        return purchaseRequired;
    }

    public void setPurchaseRequired(boolean purchaseRequired) {
        this.purchaseRequired = purchaseRequired;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isIntroCourse() {
        return introCourse;
    }

    public void setIntroCourse(boolean introCourse) {
        this.introCourse = introCourse;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
package com.learnhibernate_jpa.RESTAPIwithMySQL.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
    private String description;
    private boolean active;
    private String imageUrl;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

    public Product() {
    }

    public Product(Long id, LocalDateTime dateUpdated, LocalDateTime dateCreated, String imageUrl, boolean active, String description, String name, String sku) {
        this.id = id;
        this.dateUpdated = dateUpdated;
        this.dateCreated = dateCreated;
        this.imageUrl = imageUrl;
        this.active = active;
        this.description = description;
        this.name = name;
        this.sku = sku;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }
}

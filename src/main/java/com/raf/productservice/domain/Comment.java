package com.raf.productservice.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private String user;
    @Column(name = "product_rating")
    private Integer productRating;
    @ManyToOne
    private Product product;
    @Column(name = "created_date")
    private Instant createdDate;
    @Column(name = "updated_date")
    private Instant updatedDate;
    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    @PrePersist
    public void setCreatedDate() {
        this.createdDate = Instant.now();
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    @PreUpdate
    public void setUpdatedDate() {
        this.updatedDate = Instant.now();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

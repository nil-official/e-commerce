package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int discountPercent;

    private int quantity;

    private String brand;

    private String color;

    private boolean isFeatured = false;

    @ElementCollection
    private Set<Size> sizes = new HashSet<>();

    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    private int totalRatings = 0;

    private double averageRating = 0;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;

    @Override
    public int hashCode() {
        return Objects.hash(brand, category, color, description, discountPercent, discountedPrice, id, imageUrl,
                totalRatings, averageRating, price, quantity, ratings, reviews, sizes, title, isFeatured);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        return Objects.equals(brand, other.brand) && Objects.equals(category, other.category)
                && Objects.equals(color, other.color) && Objects.equals(description, other.description)
                && discountPercent == other.discountPercent && discountedPrice == other.discountedPrice
                && Objects.equals(id, other.id) && Objects.equals(imageUrl, other.imageUrl)
                && totalRatings == other.totalRatings && averageRating == other.averageRating
                && price == other.price && quantity == other.quantity
                && Objects.equals(ratings, other.ratings) && Objects.equals(reviews, other.reviews)
                && Objects.equals(sizes, other.sizes) && Objects.equals(title, other.title)
                && isFeatured == other.isFeatured;
    }

}

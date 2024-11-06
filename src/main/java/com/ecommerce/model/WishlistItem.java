package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Wishlist wishlist;

    @ManyToOne
    private Product product;

    private String size;

    private Long userId;

    @Override
    public int hashCode() {
        return Objects.hash(id, product, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WishlistItem other = (WishlistItem) obj;
        return Objects.equals(id, other.id) && Objects.equals(product, other.product) && Objects.equals(size, other.size);
    }

}

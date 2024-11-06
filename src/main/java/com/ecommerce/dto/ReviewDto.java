package com.ecommerce.dto;

import com.ecommerce.model.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private int totalReviews;
    private List<Review> reviews;
    private LocalDateTime createdAt;

}

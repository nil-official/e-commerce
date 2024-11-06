package com.ecommerce.dto;

import com.ecommerce.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private int totalRatings;
    private double averageRating;
    private List<Rating> ratings;
    private LocalDateTime createdAt;

}

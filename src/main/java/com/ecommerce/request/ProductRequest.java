package com.ecommerce.request;

import java.util.Set;

import com.ecommerce.model.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @Min(value = 1, message = "Price is mandatory and must be greater than 0")
    private int price;

    @Min(value = 1, message = "Discounted price is mandatory and must be greater than 0")
    private int discountedPrice;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Color is mandatory")
    private String color;

    @NotEmpty(message = "Sizes cannot be empty")
    private Set<Size> sizes;

    @NotBlank(message = "Image URL is mandatory")
    private String imageUrl;

    @NotBlank(message = "Top-level category is mandatory")
    private String topLevelCategory;

    @NotBlank(message = "Second-level category is mandatory")
    private String secondLevelCategory;

    @NotBlank(message = "Third-level category is mandatory")
    private String thirdLevelCategory;

    private boolean isFeatured;

}

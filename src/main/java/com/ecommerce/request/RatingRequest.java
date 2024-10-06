package com.ecommerce.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingRequest {

	private Long productId;
	private double rating;
	
}

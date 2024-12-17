package com.ecommerce.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {

	private Long productId;
	private String size;
	private int quantity;
	
}

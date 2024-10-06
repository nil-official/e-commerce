package com.ecommerce.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

	private Long productId;
	private String size;
	private int quantity;
//	private Integer price;
	
}

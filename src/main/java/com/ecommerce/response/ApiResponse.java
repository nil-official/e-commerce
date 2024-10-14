package com.ecommerce.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse {

	private String message;
	private boolean status;

}

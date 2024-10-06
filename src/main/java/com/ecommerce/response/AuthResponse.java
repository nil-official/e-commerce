package com.ecommerce.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponse {
	private String jwt;
	private boolean status;
}

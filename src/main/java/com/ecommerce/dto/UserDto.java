package com.ecommerce.dto;

import com.ecommerce.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private UserRole role;

    private String mobile;

//    @JsonIgnore
//    private List<Address> addresses=new ArrayList<>();

//    private List<PaymentInformation> paymentInformation=new ArrayList<>();

//    private List<Rating>ratings=new ArrayList<>();
//
//    private List<Review>reviews=new ArrayList<>();

    private LocalDateTime createdAt;
}
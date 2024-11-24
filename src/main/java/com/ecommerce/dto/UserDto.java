package com.ecommerce.dto;

import com.ecommerce.model.Order;
import com.ecommerce.model.Role;
import com.ecommerce.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
    private String mobile;
    private Date dob;
    private int totalAddresses;
    private int totalOrders;
    private int totalWishlists;
    private LocalDateTime createdAt;

}

package com.ecommerce.dto;

import com.ecommerce.model.WishlistItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {

    private Long id;
    private UserDto userDto;
    private Set<WishlistItem> wishlistItems;
    private LocalDateTime createdAt;

}

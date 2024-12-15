package com.ecommerce.utility;

import com.ecommerce.model.Size;

import java.util.Set;

public class QuantityCalculatorUtil {

    public static int getTotalQuantity(Set<Size> sizes) {
        return sizes.stream()
                .mapToInt(Size::getQuantity)
                .sum();
    }

}

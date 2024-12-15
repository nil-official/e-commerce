package com.ecommerce.utility;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationUtil {

    /**
     * Converts a list of items into a Page object based on the provided page number and size.
     *
     * @param items      The list of items to paginate.
     * @param pageNumber The page number (zero-based).
     * @param pageSize   The size of the page.
     * @param <T>        The type of the items in the list.
     * @return A Page object containing the paginated content.
     */

    public static <T> Page<T> paginateList(List<T> items, int pageNumber, int pageSize) {
        if (items == null) {
            throw new IllegalArgumentException("Items must not be null");
        }
        if (pageNumber < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Page number must be non-negative and page size must be greater than 0");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), items.size());

        if (startIndex > items.size()) {
            // If the start index exceeds the list size, return an empty page
            return new PageImpl<>(List.of(), pageable, items.size());
        }

        List<T> pageContent = items.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, pageable, items.size());
    }

}

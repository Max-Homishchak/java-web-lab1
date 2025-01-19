package com.example.spacecatsmarket.util;

import com.example.spacecatsmarket.domain.category.Category;
import com.example.spacecatsmarket.dto.category.CategoryDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryTestUtil {

    public static final Long CATEGORY_ID = 1L;
    public static final String CATEGORY_NAME = "Animal Care";

    public static Category buildCategory() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .build();
    }

    public static CategoryDto buildCategoryDto() {
        return CategoryDto.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .build();
    }

}

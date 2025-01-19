package com.example.spacecatsmarket.util;

import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.domain.category.Category;
import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.dto.product.ProductEntryDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

import static com.example.spacecatsmarket.util.CategoryTestUtil.buildCategory;
import static com.example.spacecatsmarket.util.CategoryTestUtil.buildCategoryDto;

@UtilityClass
public class ProductTestUtil {

    public static final Long PRODUCT_ID = 1L;
    public static final String PRODUCT_NAME = "Cosmic Catnip";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(9.99);
    public static final String PRODUCT_TYPE = ProductType.COSMIC_CATNIP.getDisplayName();

    public static Product buildProduct() {
        Category category = buildCategory();

        return Product.builder()
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .productType(ProductType.fromDisplayName(PRODUCT_TYPE))
                .category(category)
                .build();
    }

    public static ProductDto buildProductDto() {
        CategoryDto categoryDto = buildCategoryDto();
        return ProductDto.builder()
                .productType(PRODUCT_TYPE)
                .category(categoryDto)
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .build();
    }

    public static ProductEntryDto buildProductEntryDto() {
        CategoryDto categoryDto = buildCategoryDto();
        return ProductEntryDto.builder()
                .id(PRODUCT_ID)
                .productType(PRODUCT_TYPE)
                .category(categoryDto)
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .build();
    }

}

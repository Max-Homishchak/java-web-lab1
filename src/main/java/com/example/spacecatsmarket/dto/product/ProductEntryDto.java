package com.example.spacecatsmarket.dto.product;

import com.example.spacecatsmarket.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class ProductEntryDto {

    Long id;
    String productType;
    String name;
    BigDecimal price;
    String description;
    String imageUrl;
    CategoryDto category;

}

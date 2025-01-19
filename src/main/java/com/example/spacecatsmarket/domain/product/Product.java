package com.example.spacecatsmarket.domain.product;

import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.domain.category.Category;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class Product {

    Long id;
    ProductType productType;
    String name;
    BigDecimal price;
    String description;
    String imageUrl;
    Category category;

}

package com.example.spacecatsmarket.dto.product;

import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.dto.validation.CosmicWordCheck;
import com.example.spacecatsmarket.dto.validation.ExtendedValidation;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({ ProductDto.class, ExtendedValidation.class})
public class ProductDto {

    @NotBlank(message = "Product type is mandatory")
    String productType;

    @NotNull(message = "Category is mandatory")
    CategoryDto category;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @CosmicWordCheck(message = "Name should contain at least 1 cosmic word")
    String name;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    BigDecimal price;

    String description;
    String imageUrl;

}

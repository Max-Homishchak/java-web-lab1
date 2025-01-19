package com.example.spacecatsmarket.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryDto {

    @NotNull(message = "Category id is mandatory")
    @Positive(message = "Category id must be positive")
    Long id;

    @NotBlank(message = "Category name is mandatory")
    String name;

}

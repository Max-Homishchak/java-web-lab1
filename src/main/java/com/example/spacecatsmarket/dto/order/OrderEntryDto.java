package com.example.spacecatsmarket.dto.order;

import com.example.spacecatsmarket.dto.product.ProductEntryDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderEntryDto {

    @NotNull(message = "Product cannot be null")
    ProductEntryDto product;

    @NotNull(message = "Quantity cannot be null")
    int quantity;
}

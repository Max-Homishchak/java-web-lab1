package com.example.spacecatsmarket.dto.order;

import com.example.spacecatsmarket.common.ProductType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PlaceOrderRequestDto {

    @NotNull(message = "Entries cannot be null")
    List<OrderEntryDto> entries; // List of order entries

    @NotNull(message = "Total price cannot be null")
    @Min(value = 0)
    Double totalPrice; // Total price after any discounts or fees

}

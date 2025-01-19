package com.example.spacecatsmarket.domain.order;

import com.example.spacecatsmarket.dto.product.ProductEntryDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderEntry {

    ProductEntryDto product;
    int amount;

}

package com.example.spacecatsmarket.dto.product;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ProductListDto {

    List<ProductEntryDto> productDetailsEntries;

}

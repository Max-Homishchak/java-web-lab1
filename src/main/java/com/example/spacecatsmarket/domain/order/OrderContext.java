package com.example.spacecatsmarket.domain.order;

import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.dto.order.OrderEntryDto;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderContext {

    String cartId;
    String customerReference;
    List<OrderEntry> entries;
    Double totalPrice;
}

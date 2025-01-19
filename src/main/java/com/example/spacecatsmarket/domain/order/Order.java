package com.example.spacecatsmarket.domain.order;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Order {

    String id;
    UUID transactionId;
    List<OrderEntry> entries;
    String cartId;
    String consumerReference;
    Double totalPrice;


}

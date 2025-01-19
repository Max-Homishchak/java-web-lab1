package com.example.spacecatsmarket.domain.payment;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Payment {

    String consumerReference;
    String cartId;
    UUID paymentAssetId;
    double amount;

}

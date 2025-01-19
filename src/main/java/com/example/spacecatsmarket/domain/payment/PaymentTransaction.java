package com.example.spacecatsmarket.domain.payment;

import com.example.spacecatsmarket.common.PaymentStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentTransaction {

    UUID id;
    PaymentStatus status;
    String cartId;
}

package com.example.spacecatsmarket.dto.payment;

import com.example.spacecatsmarket.common.PaymentStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class PaymentClientRequestDto {

    String consumerReference;
    UUID paymentAssetId;
    double amount;
}

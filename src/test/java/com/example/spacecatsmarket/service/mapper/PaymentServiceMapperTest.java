package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.common.PaymentStatus;
import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.dto.payment.PaymentClientRequestDto;
import com.example.spacecatsmarket.dto.payment.PaymentClientResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentServiceMapperTest {

    PaymentServiceMapper paymentServiceMapper = Mappers.getMapper(PaymentServiceMapper.class);

    @Test
    void toPaymentClientRequestDto() {
        // given
        Payment payment = Payment.builder()
                .consumerReference("reference")
                .paymentAssetId(UUID.randomUUID())
                .amount(5.0)
                .build();

        // when
        PaymentClientRequestDto paymentClientRequestDto = paymentServiceMapper.toPaymentClientRequestDto(payment);

        // then
        assertNotNull(paymentClientRequestDto);
        assertEquals(payment.getConsumerReference(), paymentClientRequestDto.getConsumerReference());
        assertEquals(payment.getPaymentAssetId(), paymentClientRequestDto.getPaymentAssetId());
        assertEquals(payment.getAmount(), paymentClientRequestDto.getAmount());
    }

    @Test
    void toPaymentTransaction() {
        // given
        String cartId = UUID.randomUUID().toString();
        PaymentClientResponseDto paymentClientResponseDto = PaymentClientResponseDto.builder().uuid(UUID.randomUUID()).status(PaymentStatus.SUCCESS).consumerReference("reference").build();

        // when
        PaymentTransaction paymentTransaction = paymentServiceMapper.toPaymentTransaction(cartId, paymentClientResponseDto);

        // then
        assertNotNull(paymentTransaction);
        assertEquals(paymentClientResponseDto.getStatus(), paymentTransaction.getStatus());
        assertEquals(paymentClientResponseDto.getUuid(), paymentTransaction.getId());
        assertEquals(cartId, paymentTransaction.getCartId());
    }

}
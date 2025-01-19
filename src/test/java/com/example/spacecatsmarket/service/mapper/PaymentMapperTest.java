package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.domain.order.OrderContext;
import com.example.spacecatsmarket.domain.payment.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentMapperTest {

    PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void toPayment() {
        // given
        OrderContext orderContext = OrderContext.builder()
                .customerReference("reference")
                .cartId("cartId")
                .totalPrice(5.0)
                .build();

        // when
        Payment payment = paymentMapper.toPayment(orderContext);

        // then
        assertNotNull(payment);
        assertEquals(orderContext.getCustomerReference(), payment.getConsumerReference());
        assertEquals(orderContext.getCartId(), payment.getCartId());
        assertEquals(orderContext.getTotalPrice(), payment.getAmount());
    }

}
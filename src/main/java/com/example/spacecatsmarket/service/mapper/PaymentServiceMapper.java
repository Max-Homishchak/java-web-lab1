package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.dto.payment.PaymentClientRequestDto;
import com.example.spacecatsmarket.dto.payment.PaymentClientResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentServiceMapper {

    @Mapping(target = "consumerReference", source = "consumerReference")
    @Mapping(target = "paymentAssetId", source = "paymentAssetId")
    @Mapping(target = "amount", source = "amount")
    PaymentClientRequestDto toPaymentClientRequestDto(Payment payment);


    @Mapping(target = "id", source = "paymentClientResponseDto.uuid")
    @Mapping(target = "status", source = "paymentClientResponseDto.status")
    @Mapping(target = "cartId", source = "cartId")
    PaymentTransaction toPaymentTransaction(String cartId, PaymentClientResponseDto paymentClientResponseDto);
}

package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;

import java.util.List;

public interface PaymentService {

    PaymentTransaction processPayment(Payment payment);

}

package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.order.Order;
import com.example.spacecatsmarket.domain.order.OrderContext;

public interface OrderService {

    Order placeOrder(OrderContext orderContext);
}

package com.shopping.app.service;

import com.shopping.app.dto.CreateOrderResponse;
import com.shopping.app.dto.OrderResponse;
import com.shopping.app.dto.OrderTransactionResponse;
import com.shopping.app.entity.TransactionEntity;

import java.util.List;

public interface OrderService {
    public List<OrderResponse> getListOfOrdersByUserId(String userId);

    public List<OrderTransactionResponse> getListOfTransactionByUserIdAndOrderId(String userId, Integer orderId);

    public CreateOrderResponse createOrder( String userId, int qty, String coupon);
}

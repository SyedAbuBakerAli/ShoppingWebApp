package com.shopping.app.service;

import com.shopping.app.dto.TransactionResponse;


public interface TransactionService {

    public TransactionResponse processPayment(String userId, String orderId, Integer amount);
}

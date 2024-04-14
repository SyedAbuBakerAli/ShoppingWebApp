package com.shopping.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String userId;
    private Integer orderId;
    private String transactionId;
    private String status;
}

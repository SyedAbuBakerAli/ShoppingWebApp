package com.shopping.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse {

    private Integer orderId;

    private String userId;


    private int quantity;


    private int amount;


    private String coupon;
}

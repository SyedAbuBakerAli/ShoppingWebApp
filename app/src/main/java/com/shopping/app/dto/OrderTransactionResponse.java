package com.shopping.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTransactionResponse {

    private Integer orderId;
    private Integer amount;
    private String coupon;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String transactionId;
    private String status;


}

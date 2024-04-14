package com.shopping.app.controller;

import com.shopping.app.dto.CreateOrderResponse;
import com.shopping.app.dto.OrderResponse;
import com.shopping.app.dto.OrderTransactionResponse;
import com.shopping.app.dto.TransactionResponse;
import com.shopping.app.entity.CouponsEntity;
import com.shopping.app.entity.InventoryEntity;
import com.shopping.app.entity.TransactionEntity;
import com.shopping.app.service.CouponsService;
import com.shopping.app.service.InventoryService;
import com.shopping.app.service.OrderService;
import com.shopping.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class AppController {



    @Autowired
    private OrderService orderService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private CouponsService couponsService;

    @PostMapping("/{userId}/order")
    public CreateOrderResponse createOrder(@PathVariable String userId,
                                   @RequestParam int qty,
                                   @RequestParam String coupon){
     return orderService.createOrder(userId,qty,coupon);

    }

    @PostMapping("/{userId}/{orderId}/pay")
    public TransactionResponse makePayment(
            @PathVariable("userId") String userId,
            @PathVariable("orderId") String orderId,
            @RequestParam("amount") Integer amount) {
       return transactionService.processPayment(userId,orderId,amount);

    }

    @GetMapping("/inventory")
    public InventoryEntity getAppInventory(){
       return inventoryService.getInventory();
    }

    @GetMapping("/fetchCoupons")
    public CouponsEntity getCouponsList(){
      return couponsService.getCoupons();
    }

    @GetMapping("/{userId}/orders")
    public List<OrderResponse> getOrdersByUserId(@PathVariable String userId) {
       return orderService.getListOfOrdersByUserId(userId);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public List<OrderTransactionResponse> getOrdersByUserIdAndOrderId(@PathVariable String userId, @PathVariable Integer orderId) {

       return orderService.getListOfTransactionByUserIdAndOrderId(userId, orderId);
    }




}

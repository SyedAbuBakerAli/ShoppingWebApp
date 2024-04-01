package com.shopping.app.service.serviceImpl;

import com.shopping.app.dto.CreateOrderResponse;
import com.shopping.app.dto.OrderResponse;
import com.shopping.app.dto.OrderTransactionResponse;
import com.shopping.app.entity.CouponsEntity;
import com.shopping.app.entity.OrderEntity;
import com.shopping.app.entity.TransactionEntity;
import com.shopping.app.repository.CouponsRepository;
import com.shopping.app.repository.OrderRepository;
import com.shopping.app.repository.TransactionRepository;
import com.shopping.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CouponsRepository couponsRepository;
    @Override
    public List<OrderResponse> getListOfOrdersByUserId(String userId) {
        List<OrderEntity> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(OrderEntity orderEntity: orders){
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(orderEntity.getOrderId());
            orderResponse.setCoupon(orderEntity.getCoupon());
            orderResponse.setAmount(orderEntity.getAmount());
            orderResponse.setDate(orderEntity.getDate());
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }

    @Override
    public List<OrderTransactionResponse> getListOfTransactionByUserIdAndOrderId(String userId, Integer orderId) {
      List<TransactionEntity> transactionEntity = transactionRepository.findByOrderOrderId(orderId);
        OrderEntity orderEntity = orderRepository.findByOrderIdAndUserId(orderId,userId);
        List<OrderTransactionResponse> orderTransactionList = new ArrayList<>();
        if (orderEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "{\n\t\"orderId\": " + orderId + ",\n\t\"description\": \"Order not found\"\n}");
        }

        for(TransactionEntity entity: transactionEntity){

            OrderTransactionResponse response = new OrderTransactionResponse();
            response.setOrderId(orderEntity.getOrderId());
            response.setCoupon(orderEntity.getCoupon());
            response.setStatus(entity.getStatus());
            response.setAmount(orderEntity.getAmount());
            response.setTransactionId(entity.getTransactionId());
            response.setDate(orderEntity.getDate());
            orderTransactionList.add(response);
        }
        return orderTransactionList;

    }

    @Override
    public CreateOrderResponse createOrder(String userId, int qty, String coupon) {
        if (qty < 1) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "{\n\t\"description\": \"Invalid quantity\"\n}");

        }

        if (!isValidCoupon(coupon)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "{\n\t\"description\": \"Invalid coupon\"\n}");
        }
        int amount = qty * 100;
        OrderEntity order = new OrderEntity();
        CreateOrderResponse orderResponse = new CreateOrderResponse();
        order.setUserId(userId);
        order.setQuantity(qty);
        order.setAmount(amount);
        order.setCoupon(coupon);
        OrderEntity createOrder =  orderRepository.save(order);
        orderResponse.setOrderId(createOrder.getOrderId());
        orderResponse.setUserId(createOrder.getUserId());
        orderResponse.setQuantity(createOrder.getQuantity());
        orderResponse.setAmount(createOrder.getAmount());
        orderResponse.setCoupon(createOrder.getCoupon());
        return orderResponse;
    }

    private boolean isValidCoupon(String coupon) {
       List<CouponsEntity> couponsLists = couponsRepository.findAll();
        for (CouponsEntity couponEntity : couponsLists) {
            if (couponEntity.toString().contains(coupon)) {
                return true;
            }
        }
        return false;
    }


}

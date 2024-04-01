package com.shopping.app.repository;

import com.shopping.app.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    List<OrderEntity> findByUserId(String userId);

    OrderEntity findByOrderIdAndUserId(Integer orderId, String userId);
}

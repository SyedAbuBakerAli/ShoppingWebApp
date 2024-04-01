package com.shopping.app.repository;

import com.shopping.app.entity.OrderEntity;
import com.shopping.app.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Integer> {

    List<TransactionEntity> findByOrderOrderId(Integer orderId);

}

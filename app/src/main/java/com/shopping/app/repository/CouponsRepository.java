package com.shopping.app.repository;

import com.shopping.app.entity.CouponsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsRepository extends JpaRepository<CouponsEntity, Integer> {
}

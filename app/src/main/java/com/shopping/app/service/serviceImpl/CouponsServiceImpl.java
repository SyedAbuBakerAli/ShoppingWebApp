package com.shopping.app.service.serviceImpl;

import com.shopping.app.entity.CouponsEntity;
import com.shopping.app.repository.CouponsRepository;
import com.shopping.app.service.CouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponsServiceImpl implements CouponsService {
    @Autowired
    private CouponsRepository couponsRepository;
    @Override
    public CouponsEntity getCoupons() {
        List<CouponsEntity> couponsList = couponsRepository.findAll();
        CouponsEntity coupons = new CouponsEntity();
        for(CouponsEntity couponsEntity: couponsList){
            coupons.setOFF5(couponsEntity.getOFF5());
            coupons.setOFF10(couponsEntity.getOFF10());
            coupons.setId(coupons.getId());
        }
        return coupons;
    }
}

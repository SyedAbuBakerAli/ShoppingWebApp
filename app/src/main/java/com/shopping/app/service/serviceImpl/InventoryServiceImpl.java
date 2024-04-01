package com.shopping.app.service.serviceImpl;

import com.shopping.app.entity.InventoryEntity;
import com.shopping.app.repository.InventoryRepository;
import com.shopping.app.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Override
    public InventoryEntity getInventory() {
        List<InventoryEntity> inventoryList  = inventoryRepository.findAll();
        InventoryEntity inventoryResult = new InventoryEntity();
        for(InventoryEntity inventory : inventoryList ){
            inventoryResult.setOrdered(inventory.getOrdered());
            inventoryResult.setPrice(inventory.getPrice());
            inventoryResult.setAvailable(inventory.getAvailable());

        }
        return inventoryResult;
    }
}

package com.example.CanteenManagement.service;

import com.example.CanteenManagement.entity.InventoryItem;
import com.example.CanteenManagement.repository.InventoryItemRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class InventoryItemService {

    private final InventoryItemRepo inventoryItemRepo;

    public InventoryItemService(InventoryItemRepo inventoryItemRepo) {
        this.inventoryItemRepo = inventoryItemRepo;
    }

    public InventoryItem createInventoryItem(InventoryItem inventoryItem){
        return inventoryItemRepo.save(inventoryItem);
    }

    public List<InventoryItem> getAll(){
        return inventoryItemRepo.findAll();
    }

    public InventoryItem getInventoryById(Long id) {
        return inventoryItemRepo.findById(id).get();
    }

    public InventoryItem updateInventoryItem(InventoryItem inventoryItemDetails, Long id){
        InventoryItem inventoryItem = getInventoryById(id);
        inventoryItem.setQuantity(inventoryItemDetails.getQuantity());
        inventoryItem.setFlavour(inventoryItemDetails.getFlavour());
        return inventoryItemRepo.save(inventoryItem);
    }

    public void deleteInventoryItem (Long id) {
        inventoryItemRepo.deleteById(id);
    }
}

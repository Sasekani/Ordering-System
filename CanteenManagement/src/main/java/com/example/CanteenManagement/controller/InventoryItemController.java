package com.example.CanteenManagement.controller;

import com.example.CanteenManagement.entity.InventoryItem;
import com.example.CanteenManagement.service.InventoryItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/inventory")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @GetMapping
    public List<InventoryItem> getInventoryItems() {
        return inventoryItemService.getAll();

    }

    @PostMapping
    public InventoryItem createNewFlavour(@RequestBody() InventoryItem inventoryItem) {
        return inventoryItemService.createInventoryItem(inventoryItem);
    }

    @PutMapping("/{id}")
    public InventoryItem updateFlavour(@RequestBody() InventoryItem inventoryItem, @PathVariable Long id) {
        return inventoryItemService.updateInventoryItem(inventoryItem, id);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryItem (@PathVariable Long id) {
        inventoryItemService.deleteInventoryItem(id);
    }

}

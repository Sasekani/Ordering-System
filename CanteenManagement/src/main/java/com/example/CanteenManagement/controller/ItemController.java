package com.example.CanteenManagement.controller;

import com.example.CanteenManagement.entity.Flavour;
import com.example.CanteenManagement.entity.InventoryItem;
import com.example.CanteenManagement.entity.Item;
import com.example.CanteenManagement.repository.ItemRepo;
import com.example.CanteenManagement.service.FlavourService;
import com.example.CanteenManagement.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {


    private final ItemRepo itemRepo;


    private final ItemService itemService;

    public ItemController(ItemRepo itemRepo, ItemService itemService) {
        this.itemRepo = itemRepo;
        this.itemService = itemService;
    }



    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItemById (@PathVariable Long id) {
        return itemService.getItemById(id);
    }


    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);

    }




}

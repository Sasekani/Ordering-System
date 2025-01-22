package com.example.CanteenManagement.service;

import com.example.CanteenManagement.entity.Flavour;
import com.example.CanteenManagement.entity.Item;
import com.example.CanteenManagement.repository.ItemRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Slf4j
@Service
public class ItemService {


    private final ItemRepo itemRepo;

    public ItemService(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }



    public List<Item> getAllItems() {

        return itemRepo.findAll();
    }

    public Item getItemById(Long id){

        return itemRepo.findById(id).orElse(null);
    }

    public String deleteItem(Long id) {

        itemRepo.deleteById(id);
        return  id + "Menu Item Removed";
    }


}

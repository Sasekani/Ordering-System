package com.example.CanteenManagement.repository;

import com.example.CanteenManagement.entity.InventoryItem;
import com.example.CanteenManagement.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepo extends JpaRepository<InventoryItem, Long> {

}

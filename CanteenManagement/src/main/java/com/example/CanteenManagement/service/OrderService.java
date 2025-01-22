package com.example.CanteenManagement.service;

import com.example.CanteenManagement.entity.InventoryItem;
import com.example.CanteenManagement.entity.Item;
import com.example.CanteenManagement.entity.Order;
import com.example.CanteenManagement.repository.InventoryItemRepo;
import com.example.CanteenManagement.repository.ItemRepo;
import com.example.CanteenManagement.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {


    private final OrderRepo orderRepo;
    private final ItemRepo itemRepo;
    private final InventoryItemRepo inventoryItemRepo;

    public OrderService(OrderRepo orderRepo, ItemRepo itemRepo, InventoryItemRepo inventoryItemRepo) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;

        this.inventoryItemRepo = inventoryItemRepo;
    }


    public Order createOrder(Order order) {
        Order savedOrder= orderRepo.save(order);


        savedOrder.getItems().forEach(item -> {
            item.setOrder(savedOrder);
            itemRepo.save(item);
        });


        return savedOrder;
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));

        order.setStatus(status);
        return orderRepo.save(order);
    }


    public Order getOrderById (Long id) {

        return orderRepo.findById(id).orElse(null);
    }

    public List<Order> getAllOrders () {

        return orderRepo.findAll();
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepo.findByUserUserId(userId);
    }

    public Order updateOrder(Long orderId, Order order) {

        if (orderRepo.existsById(orderId)) {
            order.setOrderId(orderId);
            return orderRepo.save(order);
        }

        return null;
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }
}

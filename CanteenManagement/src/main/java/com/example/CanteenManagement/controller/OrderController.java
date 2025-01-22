package com.example.CanteenManagement.controller;

import com.example.CanteenManagement.entity.Order;
import com.example.CanteenManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);

    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status must not be null or empty");
        }
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);

    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }

    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);

    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

}


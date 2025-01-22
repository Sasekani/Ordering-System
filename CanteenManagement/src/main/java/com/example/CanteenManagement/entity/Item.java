package com.example.CanteenManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDEREDITEMS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    @JsonIgnore
    private Long id;



    @ManyToOne
    @JoinColumn(name="inventoryId")
    private InventoryItem inventoryItem;

    @ManyToOne
    @JoinColumn(name="orderId")
    @JsonIgnore
    private Order order;

}



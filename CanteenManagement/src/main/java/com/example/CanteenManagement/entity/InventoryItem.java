package com.example.CanteenManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="INVENTORY")
@Entity
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="inventoryId")
    private Long id;
    private String name;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "flavourId")
    private Flavour flavour;

}

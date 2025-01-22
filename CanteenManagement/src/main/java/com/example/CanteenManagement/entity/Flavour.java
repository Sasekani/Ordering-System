package com.example.CanteenManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name="FLAVOUR")
public class Flavour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="flavourId")
    private Long id;
    private String description;


    public Long getId() {
        return id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

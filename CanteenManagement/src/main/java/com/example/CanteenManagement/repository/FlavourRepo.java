package com.example.CanteenManagement.repository;

import com.example.CanteenManagement.entity.Flavour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlavourRepo extends JpaRepository<Flavour, Long> {
}

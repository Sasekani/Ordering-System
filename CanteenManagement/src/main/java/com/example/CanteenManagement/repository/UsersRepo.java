package com.example.CanteenManagement.repository;


import com.example.CanteenManagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {

    Optional<Users> findByUserName(String username);

    boolean existsByUserName(String userName);
}

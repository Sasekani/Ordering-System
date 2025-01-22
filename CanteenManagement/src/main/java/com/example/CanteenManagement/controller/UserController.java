package com.example.CanteenManagement.controller;

import com.example.CanteenManagement.datatransferobject.RequestRespond;
import com.example.CanteenManagement.entity.Users;
import com.example.CanteenManagement.service.UsersManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UsersManagementService usersManagementService;

    public UserController(UsersManagementService usersManagementService) {
        this.usersManagementService = usersManagementService;
    }


    @PostMapping("/register")
    public ResponseEntity<RequestRespond> register(@RequestBody RequestRespond reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/login")
    public ResponseEntity<RequestRespond> login(@RequestBody RequestRespond req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RequestRespond> refreshToken(@RequestBody RequestRespond req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }


    @GetMapping
    public ResponseEntity<RequestRespond> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RequestRespond> getUserByID(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<RequestRespond> updateUser(@PathVariable Integer userId, @RequestBody Users reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RequestRespond> deleteUser(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }


    @GetMapping("/profile")
    public ResponseEntity<RequestRespond> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestRespond response = usersManagementService.getMyInfo(email);

        if (response.getUsers() != null) {
            return ResponseEntity.ok(response); // HTTP 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // HTTP 404 Not Found
        }
    }

}

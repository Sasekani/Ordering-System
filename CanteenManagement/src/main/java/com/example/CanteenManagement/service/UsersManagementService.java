package com.example.CanteenManagement.service;

import com.example.CanteenManagement.datatransferobject.RequestRespond;
import com.example.CanteenManagement.entity.Role;
import com.example.CanteenManagement.entity.Users;
import com.example.CanteenManagement.repository.UsersRepo;
import com.example.CanteenManagement.utilities.JWTUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initDummyData() {
        Users user1 = new Users();
        user1.setUserName("admin");
        user1.setFirstName("Admin");
        user1.setLastName("Canteen");
        user1.setEmail("Admin@example.com");
        user1.setPassword(passwordEncoder.encode("admin123$"));
        user1.setRole(Role.ADMIN);

        Users user2 = new Users();
        user2.setUserName("kitchen");
        user2.setFirstName("KitchenStaff");
        user2.setLastName("Canteen");
        user2.setEmail("KitchenStaff@example.com");
        user2.setPassword(passwordEncoder.encode("kitchen123$"));
        user2.setRole(Role.KITCHENSTAFF);

        Users user3 = new Users();
        user3.setUserName("employee");
        user3.setFirstName("Employee");
        user3.setLastName("Canteen");
        user3.setEmail("Employee@example.com");
        user3.setPassword(passwordEncoder.encode("employee123$"));
        user3.setRole(Role.EMPLOYEE);

        usersRepo.save(user1);
        usersRepo.save(user2);
        usersRepo.save(user3);
    }


    public RequestRespond register(RequestRespond registrationRequest) {
        RequestRespond resp = new RequestRespond();

        try {
            // Check if any required field is null
            if (registrationRequest.getUserName() == null || registrationRequest.getFirstName() == null ||
                    registrationRequest.getLastName() == null || registrationRequest.getEmail() == null ||
                    registrationRequest.getPassword() == null || registrationRequest.getRole() == null) {
                resp.setMessage("All fields are required");
                return resp;
            }

            // Check if username already exists
            if (usersRepo.existsByUserName(registrationRequest.getUserName())) {
                resp.setMessage("Username is already taken");
                return resp;
            }

            Users users = new Users();
            users.setUserName(registrationRequest.getUserName());
            users.setFirstName(registrationRequest.getFirstName());
            users.setLastName(registrationRequest.getLastName());
            users.setEmail(registrationRequest.getEmail());

            Role role = Role.valueOf(registrationRequest.getRole().toUpperCase());
            users.setRole(role);

            // HashPassword
            users.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            Users usersResult = usersRepo.save(users);

            if (usersResult.getUserId() > 0) {
                resp.setUsers(usersResult);
                resp.setMessage("User Saved Successfully");
            }

        } catch (Exception e) {
            resp.setMessage("An error occurred: " + e.getMessage());
        }
        return resp;
    }





    public RequestRespond login(RequestRespond loginRequest){
        RequestRespond response = new RequestRespond();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                            loginRequest.getPassword()));
            var user = usersRepo.findByUserName(loginRequest.getUserName()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole().name());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage(response.getRole() + " Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    

    public RequestRespond refreshToken(RequestRespond refreshTokenRequest){
        RequestRespond response = new RequestRespond();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            Users users = usersRepo.findByUserName(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public RequestRespond getAllUsers() {
        RequestRespond reqRes = new RequestRespond();

        try {
            List<Users> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public RequestRespond getUsersById(Integer id) {
        RequestRespond reqRes = new RequestRespond();
        try {
            Users usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setUsers(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public RequestRespond deleteUser(Integer userId) {
        RequestRespond reqRes = new RequestRespond();
        try {
            Optional<Users> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public RequestRespond updateUser(Integer userId, Users updatedUsers) {
        RequestRespond reqRes = new RequestRespond();
        try {
            Optional<Users> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                Users existingUsers = userOptional.get();
//                existingUsers.setUserName(updatedUsers.getUsername());
                existingUsers.setFirstName(updatedUsers.getFirstName());
                existingUsers.setLastName(updatedUsers.getLastName());
                existingUsers.setRole(updatedUsers.getRole());

                // Check if password is present in the request
                if (updatedUsers.getPassword() != null && !updatedUsers.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUsers.setPassword(passwordEncoder.encode(updatedUsers.getPassword()));
                }

                Users savedUsers = usersRepo.save(existingUsers);
                reqRes.setUsers(savedUsers);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public RequestRespond getMyInfo(String email){
        RequestRespond reqRes = new RequestRespond();
        try {
            Optional<Users> userOptional = usersRepo.findByUserName(email);
            if (userOptional.isPresent()) {
                reqRes.setUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
}

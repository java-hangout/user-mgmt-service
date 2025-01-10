package com.jh.tds.ums.controller;

import com.jh.tds.ums.exception.ErrorResponse;
import com.jh.tds.ums.model.User;
import com.jh.tds.ums.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow only your frontend URL
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("registerUser --->>>" + user);

        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (RuntimeException e) {
            // Catch the exception and return the error message dynamically from the service
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);  // Return error if username already exists
        }
    }

    @GetMapping("/fetch/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);  // Return the user if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return NOT_FOUND if user doesn't exist
        }
    }

    // Get all departments
    @GetMapping("/fetch/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> departments = userService.getAllUsers();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/fetch/username/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);  // Return the user if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return NOT_FOUND if user doesn't exist
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.deleteById(id);  // Pass the userId (e.g., user_002) to the service
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Success, 204 No Content
    }
}

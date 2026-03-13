package com.auth.controller;

import com.auth.dto.UserDTO;
import com.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // create user
    // "/api/v1/users"
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.creatUser(userDTO));
    }

    // get all users
    // "/api/v1/users"
    @GetMapping
    public ResponseEntity<Iterable<UserDTO>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    // get user by email
    // /api/v1/users/{emailId}
    @GetMapping("/email/{emailId}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("emailId") String emailId){
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByEmail(emailId));
    }

    // get user by id
    // /api/v1/users/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserById(userId));
    }

    // update user
    // api/v1/users/{userId}
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDTO,userId));
    }

    // delete user
    // api/v1/users/{userId}
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

}

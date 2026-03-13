package com.auth.service;

import com.auth.dto.UserDTO;

import java.util.UUID;

public interface UserService {

    //create user
    UserDTO creatUser(UserDTO userDTO);

    // get all users
    Iterable<UserDTO> getAllUsers();

    //get user by email
    UserDTO getUserByEmail(String email);

    //get user by id
    UserDTO getUserById(String userId);

    // update user
    UserDTO updateUser(UserDTO userDTO, String userId);

    // delete user
   void deleteUser(String userId);

}

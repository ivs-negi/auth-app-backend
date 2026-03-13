package com.auth.serviceImpl;

import com.auth.dto.UserDTO;
import com.auth.entity.Provider;
import com.auth.entity.User;
import com.auth.exception.IllegalArgumentException;
import com.auth.exception.ResourceNotFoundException;
import com.auth.helper.UserHelper;
import com.auth.repository.UserRepository;
import com.auth.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    // ***** create user *****
    @Override
    @Transactional
    public UserDTO creatUser(UserDTO userDTO) {

        // checking if name is already exists
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // mapping DTO to user entity
        User user = modelMapper.map(userDTO, User.class);

        // Set provider (default to LOCAL if not provided)
        user.setProvider(userDTO.getProvider() != null ? userDTO.getProvider() : Provider.LOCAL);

        // saving user
        User savedUser = userRepository.save(user);

        // mapping entity to DTO
        return modelMapper.map(savedUser, UserDTO.class);
    }

    // ***** get all users *****
    @Override
    public Iterable<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    // ***** get user by email *****
    @Override
    public UserDTO getUserByEmail(String email) {

        // checking if user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found with email: " + email));

        return modelMapper.map(user, UserDTO.class);
    }

    // ***** get user by id *****
    @Override
    public UserDTO getUserById(String userId) {

        UUID uId = UserHelper.parseUUID(userId);

        // checking if id exists
        User user = userRepository.findById(uId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        return modelMapper.map(user, UserDTO.class);
    }

    // ***** update user *****
    @Override
    public UserDTO updateUser(UserDTO userDTO, String userId) {
        UUID uId = UserHelper.parseUUID(userId);

        User existingUser = userRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if(userDTO.getName()!=null) existingUser.setName(userDTO.getName());
        if (userDTO.getPassword()!=null) existingUser.setPassword(userDTO.getPassword());
        if(userDTO.getImage()!=null) existingUser.setImage(userDTO.getImage());
        if(userDTO.getProvider()!=null) existingUser.setProvider(userDTO.getProvider());
        existingUser.setEnable(userDTO.isEnable());

        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    // ***** delete user *****
    @Override
    public void deleteUser(String userId) {

        UUID uId = UserHelper.parseUUID(userId);

        // checking id
        User user = userRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        userRepository.delete(user);
    }
}

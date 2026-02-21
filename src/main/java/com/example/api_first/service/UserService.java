/**
 * Service layer for user-related business logic.
 */
package com.example.api_first.service;

import com.example.api_first.entity.User;
import com.example.api_first.mapper.UserMapper;
import com.example.api_first.repository.UserRepository;
import com.example.model.CreateUserRequestDTO;
import com.example.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for user-related business logic.
 * Acts as an intermediary between the controller and repository layers.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Repository for user data access.
     */
    private final UserRepository userRepository;

    /**
     * Mapper for entity-DTO conversions.
     */
    private final UserMapper userMapper;

    /**
     * Retrieves all users.
     *
     * @return a list of all users as DTOs.
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user.
     * @return an Optional containing the user DTO if found, or empty if not found.
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    /**
     * Creates a new user based on the provided request data.
     *
     * @param request the request containing user creation data.
     * @return the newly created user DTO with generated ID and timestamps.
     */
    @Transactional
    public UserDTO createUser(CreateUserRequestDTO request) {
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}

package com.example.api_first.service;

import com.example.api_first.repository.UserRepo;
import com.example.model.CreateUserRequestDTO;
import com.example.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for user-related business logic.
 * Acts as an intermediary between the controller and repository layers.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<UserDTO> getUserById(Long id) {
        return userRepo.findById(id);
    }

    /**
     * Creates a new user based on the provided request data.
     *
     * @param request the request containing user creation data
     * @return the newly created user with generated ID and timestamp
     */
    public UserDTO createUser(CreateUserRequestDTO request) {
        UserDTO user = new UserDTO()
                .username(request.getUsername())
                .email(request.getEmail());
        return userRepo.save(user);
    }
}

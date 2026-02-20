package com.example.api_first.controller;

import com.example.api.UsersApi;
import com.example.api_first.controller.base.AbstractController;
import com.example.api_first.service.UserService;
import com.example.model.CreateUserRequestDTO;
import com.example.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for user management endpoints.
 * Implements the generated UsersApi interface from OpenAPI specification.
 */
@RestController
@RequiredArgsConstructor
public class UserController extends AbstractController implements UsersApi {

    private final UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<UserDTO> getUserById(Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<UserDTO> createUser(CreateUserRequestDTO request) {
        UserDTO created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}

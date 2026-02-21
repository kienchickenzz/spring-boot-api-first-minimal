/**
 * Mapper for converting between User entity and UserDTO.
 */
package com.example.api_first.mapper;

import com.example.api_first.entity.User;
import com.example.model.CreateUserRequestDTO;
import com.example.model.UserDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper class for User entity and DTO conversions.
 * Handles bidirectional mapping between persistence and API layers.
 */
@Component
public class UserMapper {

    /**
     * Converts a User entity to UserDTO.
     *
     * @param user the User entity to convert.
     * @return the corresponding UserDTO.
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO()
                .id(user.getId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt());
    }

    /**
     * Converts a CreateUserRequestDTO to User entity.
     *
     * @param request the request DTO containing user creation data.
     * @return the corresponding User entity.
     */
    public User toEntity(CreateUserRequestDTO request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
}

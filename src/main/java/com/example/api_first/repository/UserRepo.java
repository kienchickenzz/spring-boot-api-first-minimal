package com.example.api_first.repository;

import com.example.model.UserDTO;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository class for managing User entities.
 * Currently uses in-memory storage for demonstration purposes.
 */
@Repository
public class UserRepo {

    private final List<UserDTO> users = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users
     */
    public List<UserDTO> findAll() {
        System.out.println("[UserRepo] findAll() - returning " + users.size() + " users");
        return new ArrayList<>(users);
    }

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<UserDTO> findById(Long id) {
        System.out.println("[UserRepo] findById(" + id + ")");
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    /**
     * Saves a user to the repository.
     * If the user has no ID, a new ID will be generated and createdAt timestamp will be set.
     *
     * @param user the user to save
     * @return the saved user with generated ID and timestamp
     */
    public UserDTO save(UserDTO user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
            user.setCreatedAt(OffsetDateTime.now());
        }
        users.add(user);
        System.out.println("[UserRepo] save() - saved user: " + user);
        return user;
    }
}

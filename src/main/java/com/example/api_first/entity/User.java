/**
 * User entity representing application users with authentication credentials.
 */
package com.example.api_first.entity;

import com.example.api_first.entity.base.AbstractEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User entity representing application users.
 * Stores authentication credentials and basic user information.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractEntity {

    /**
     * Encrypted password for authentication.
     */
    @Column(nullable = false)
    private String password;

    /**
     * User email address.
     */
    @Column(unique = true)
    private String email;
}

/**
 * Abstract base entity providing common audit fields for all entities.
 */
package com.example.api_first.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

/**
 * Abstract base entity providing common audit fields.
 * All entities should extend this class to inherit id, createdAt, and updatedAt.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    /**
     * Primary key identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Timestamp when the entity was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    /**
     * Timestamp when the entity was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}

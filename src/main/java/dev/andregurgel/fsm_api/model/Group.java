package dev.andregurgel.fsm_api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "public", name = "\"group\"")
public class Group {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_owner_id", nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private User owner;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(name = "uk_group_user", columnNames = {"group_id", "user_id"})
    )
    @JsonIncludeProperties({"id", "name"})
    private Set<User> users = new HashSet<>();

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }
}

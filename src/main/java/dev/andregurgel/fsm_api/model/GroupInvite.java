package dev.andregurgel.fsm_api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "public", name = "group_invite")
public class GroupInvite {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private Group group;

    @NotNull
    private Integer uses;

    @NotNull
    private UUID hash;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (hash == null) {
            hash = UUID.randomUUID();
        }
    }
}

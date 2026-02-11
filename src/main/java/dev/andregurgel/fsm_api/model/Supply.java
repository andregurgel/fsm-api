package dev.andregurgel.fsm_api.model;

import dev.andregurgel.fsm_api.model.enums.SupplyTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "public", name = "supply")
public class Supply {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal liter;

    @NotNull
    private BigDecimal price;

    @Column(name = "current_mileage")
    private Integer currentMileage;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SupplyTypeEnum type;

    @NotNull
    @Column(name = "supplied_at")
    private LocalDateTime suppliedAt;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    private Boolean deleted = false;

    @Column(name = "deleted_At")
    private LocalDateTime deletedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }
}

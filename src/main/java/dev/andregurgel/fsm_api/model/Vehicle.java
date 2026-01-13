package dev.andregurgel.fsm_api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import dev.andregurgel.fsm_api.model.enums.TypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "public", name = "vehicle")
public class Vehicle {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    private String plate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private Group group;
}

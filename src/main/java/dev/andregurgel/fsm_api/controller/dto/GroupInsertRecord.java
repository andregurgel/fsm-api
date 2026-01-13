package dev.andregurgel.fsm_api.controller.dto;

import jakarta.validation.constraints.NotNull;

public record GroupInsertRecord(
        @NotNull Long ownerId, // TODO: Remove after token jwt implementation, get owner information from token.
        @NotNull String name
) { }

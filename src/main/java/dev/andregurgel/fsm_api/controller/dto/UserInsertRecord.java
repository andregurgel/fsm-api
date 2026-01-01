package dev.andregurgel.fsm_api.controller.dto;

import jakarta.validation.constraints.NotNull;

public record UserInsertRecord(
        @NotNull String name,
        @NotNull String email,
        @NotNull String password,
        String phone
) { }

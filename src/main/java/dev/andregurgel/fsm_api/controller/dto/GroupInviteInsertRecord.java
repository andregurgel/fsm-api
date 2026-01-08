package dev.andregurgel.fsm_api.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record GroupInviteInsertRecord(
        @NotNull Long groupId,
        @NotNull LocalDateTime expiresAt
        ) {
}

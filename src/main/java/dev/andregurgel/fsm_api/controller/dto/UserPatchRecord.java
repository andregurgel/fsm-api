package dev.andregurgel.fsm_api.controller.dto;

public record UserPatchRecord(
        String name,
        String email,
        String phone
) { }

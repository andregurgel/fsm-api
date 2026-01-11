package dev.andregurgel.fsm_api.commons.exception.data_integrity_violation;

public record PgIntegrityInfo(boolean uniqueViolation, String constraintName) {
}

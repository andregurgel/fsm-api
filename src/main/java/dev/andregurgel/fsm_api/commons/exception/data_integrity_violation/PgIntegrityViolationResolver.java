package dev.andregurgel.fsm_api.commons.exception.data_integrity_violation;

import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;

public class PgIntegrityViolationResolver {

    private static final String PG_UNIQUE_VIOLATION = "23505";

    private PgIntegrityViolationResolver() {}

    public static PgIntegrityInfo resolve(DataIntegrityViolationException ex) {
        Throwable root = rootCause(ex);

        boolean unique = hasSqlState(ex, PG_UNIQUE_VIOLATION) || looksLikePgUnique(root);

        // tenta pegar constraintName do Hibernate (quando disponível) ou por parsing da mensagem do Postgres
        String constraintName = extractConstraintName(ex);
        if (constraintName == null) {
            constraintName = extractConstraintNameFromMessage(root);
        }

        return new PgIntegrityInfo(unique, constraintName);
    }

    private static boolean hasSqlState(Throwable t, String sqlState) {
        Throwable cur = t;
        while (cur != null) {
            if (cur instanceof SQLException sqlEx && sqlState.equals(sqlEx.getSQLState())) {
                return true;
            }
            cur = cur.getCause();
        }
        return false;
    }

    private static boolean looksLikePgUnique(Throwable t) {
        if (t == null || t.getMessage() == null) return false;
        String msg = t.getMessage().toLowerCase();
        return msg.contains("duplicate key value violates unique constraint");
    }

    /**
     * Se você usa Hibernate, às vezes o nome da constraint aparece aqui.
     * Sem depender diretamente de classes do Hibernate, tentamos refletir.
     */
    private static String extractConstraintName(Throwable t) {
        Throwable cur = t;
        while (cur != null) {
            // org.hibernate.exception.ConstraintViolationException tem getConstraintName()
            if (cur.getClass().getName().equals("org.hibernate.exception.ConstraintViolationException")) {
                try {
                    Object name = cur.getClass().getMethod("getConstraintName").invoke(cur);
                    return name != null ? name.toString() : null;
                } catch (Exception ignored) {}
            }
            cur = cur.getCause();
        }
        return null;
    }

    /**
     * Postgres: duplicate key value violates unique constraint "SUA_CONSTRAINT"
     */
    private static String extractConstraintNameFromMessage(Throwable root) {
        if (root == null || root.getMessage() == null) return null;
        String msg = root.getMessage();

        int idx = msg.toLowerCase().indexOf("unique constraint");
        if (idx < 0) return null;

        int firstQuote = msg.indexOf('"', idx);
        int secondQuote = firstQuote >= 0 ? msg.indexOf('"', firstQuote + 1) : -1;

        if (firstQuote >= 0 && secondQuote > firstQuote) {
            return msg.substring(firstQuote + 1, secondQuote);
        }
        return null;
    }

    private static Throwable rootCause(Throwable t) {
        Throwable cur = t;
        while (cur.getCause() != null && cur.getCause() != cur) {
            cur = cur.getCause();
        }
        return cur;
    }
}

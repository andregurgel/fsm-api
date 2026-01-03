package dev.andregurgel.fsm_api.commons.infrastructure.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class SpecificationUtils {

    private SpecificationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Expression<String> unaccent(CriteriaBuilder cb, Expression<String> expression) {
        return cb.function("unaccent", String.class, expression);
    }

    public static Predicate likeIgnoreCaseAndAccent(CriteriaBuilder cb, Expression<String> attribute, String value) {
        return cb.like(
                unaccent(cb, cb.lower(attribute)),
                unaccent(cb, cb.literal("%" + value.toLowerCase() + "%"))
        );
    }
}

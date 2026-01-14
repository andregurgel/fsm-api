package dev.andregurgel.fsm_api.repository.spec;

import dev.andregurgel.fsm_api.controller.filter.SupplyFilter;
import dev.andregurgel.fsm_api.model.Supply;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SupplySpecification {

    public static Specification<Supply> filter(SupplyFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("deleted"), false));

            query.orderBy(cb.desc(root.get("createdAt")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

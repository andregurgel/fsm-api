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

            if (filter.getGroupId() != null) {
                predicates.add(cb.equal(root.get("vehicle").get("group").get("id"), filter.getGroupId()));
            }

            if (filter.getVehicleId() != null) {
                predicates.add(cb.equal(root.get("vehicle").get("id"), filter.getVehicleId()));
            }

            if (filter.getUserId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUserId()));
            }

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("suppliedAt"), filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("suppliedAt"), filter.getEndDate()));
            }

            query.orderBy(cb.desc(root.get("suppliedAt")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

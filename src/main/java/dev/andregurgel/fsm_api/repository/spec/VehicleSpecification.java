package dev.andregurgel.fsm_api.repository.spec;

import dev.andregurgel.fsm_api.controller.filter.VehicleFilter;
import dev.andregurgel.fsm_api.model.Vehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> filter(VehicleFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }

            if (filter.getGroupId() != null) {
                predicates.add(cb.equal(root.get("group").get("id"), filter.getGroupId()));
            }

            if (filter.getType() != null) {
                predicates.add(cb.equal(root.get("type"), filter.getType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

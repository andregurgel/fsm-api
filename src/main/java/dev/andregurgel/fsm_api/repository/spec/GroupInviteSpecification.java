package dev.andregurgel.fsm_api.repository.spec;

import dev.andregurgel.fsm_api.commons.infrastructure.util.SpecificationUtils;
import dev.andregurgel.fsm_api.controller.filter.GroupInviteFilter;
import dev.andregurgel.fsm_api.model.GroupInvite;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupInviteSpecification {

    public static Specification<GroupInvite> filter(GroupInviteFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }

            if (filter.getGroupId() != null) {
                predicates.add(cb.equal(root.get("group").get("id"), filter.getGroupId()));
            }

            predicates.add(cb.lessThan(root.get("expiresAt"), LocalDateTime.now()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

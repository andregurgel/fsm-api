package dev.andregurgel.fsm_api.repository;

import dev.andregurgel.fsm_api.model.GroupInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface GroupInviteRepository extends JpaRepository<GroupInvite, Long>, JpaSpecificationExecutor<GroupInvite> {
    Optional<GroupInvite> findByHash(UUID hash);
}

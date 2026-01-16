package dev.andregurgel.fsm_api.repository;

import dev.andregurgel.fsm_api.model.KeyPairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyPairEntityRepository extends JpaRepository<KeyPairEntity, Long> {
    Optional<KeyPairEntity> findTopByOrderByCreatedAtDesc();
}

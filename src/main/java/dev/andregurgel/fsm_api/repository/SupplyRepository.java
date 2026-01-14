package dev.andregurgel.fsm_api.repository;

import dev.andregurgel.fsm_api.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, UUID>, JpaSpecificationExecutor<Supply> {
}

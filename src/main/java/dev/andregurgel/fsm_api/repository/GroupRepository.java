package dev.andregurgel.fsm_api.repository;

import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByOwner_IdAndUsersContains(Long ownerId, User user);
    List<Group> findAllByOwner_Id(Long ownerId);
}

package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.controller.dto.GroupInserRecord;
import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserService userService;

    public GroupService(GroupRepository groupRepository,
                        UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public List<Group> findAllFromUser(Long userId) {
        User user = new User();
        user.setId(userId);

        return groupRepository.findAllByOwner_IdAndUsersContains(userId, user);
    }

    @Transactional
    public Group insert(GroupInserRecord groupInserRecord) {
        verifyIfUserCanCreateGroup(groupInserRecord.ownerId());

        User owner = userService.findById(groupInserRecord.ownerId());

        Group group = new Group();
        group.setName(groupInserRecord.name());
        group.setOwner(owner);

        group.getUsers().add(owner);

        return groupRepository.save(group);
    }

    private void verifyIfUserCanCreateGroup(Long ownerId) {
        List<Group> groups = groupRepository.findAllByOwner_Id(ownerId);
        if (groups.size() == 2) {
            throw new RuntimeException("Você atingiu o limite de 2 grupos criados.");
        }
    }
}

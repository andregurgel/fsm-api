package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.commons.exception.ApplicationException;
import dev.andregurgel.fsm_api.controller.dto.GroupInserRecord;
import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserService userService;

    private final MessageService messageService;

    public GroupService(GroupRepository groupRepository,
                        UserService userService,
                        MessageService messageService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.messageService = messageService;
    }

    public Group findById(Long groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isEmpty()) {
            throw new ApplicationException(messageService.get("group.not.found.exception", groupId));
        }

        return groupOpt.get();
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

    @Transactional
    public void addUserToGroup(Group group, User user) {
        group.getUsers().add(user);
        groupRepository.save(group);
    }

    private void verifyIfUserCanCreateGroup(Long ownerId) {
        List<Group> groups = groupRepository.findAllByOwner_Id(ownerId);
        if (groups.size() == 2) {
            throw new RuntimeException(messageService.get("group.creation.limit.exceeded.exception"));
        }
    }
}

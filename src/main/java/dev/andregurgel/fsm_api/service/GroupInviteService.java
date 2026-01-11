package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.commons.exception.ApplicationException;
import dev.andregurgel.fsm_api.controller.dto.GroupInviteInsertRecord;
import dev.andregurgel.fsm_api.controller.filter.GroupInviteFilter;
import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.model.GroupInvite;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.repository.GroupInviteRepository;
import dev.andregurgel.fsm_api.repository.spec.GroupInviteSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupInviteService {

    private final GroupInviteRepository groupInviteRepository;

    private final GroupService groupService;

    private final UserService userService;

    private final MessageService messageService;

    public GroupInviteService(GroupInviteRepository groupInviteRepository,
                              GroupService groupService,
                              UserService userService,
                              MessageService messageService) {
        this.groupInviteRepository = groupInviteRepository;
        this.groupService = groupService;
        this.userService = userService;
        this.messageService = messageService;
    }

    public GroupInvite findByHash(UUID hash) {
        Optional<GroupInvite> groupInviteOpt = groupInviteRepository.findByHash(hash);
        if (groupInviteOpt.isEmpty()) {
            throw new ApplicationException(messageService.get("groupInvite.not.found.exception", hash));
        }

        return groupInviteOpt.get();
    }

    public Page<GroupInvite> findAllPageable(Pageable pageable) {
        return groupInviteRepository.findAll(pageable);
    }

    public Page<GroupInvite> findAllPageableFiltered(Pageable pageable, GroupInviteFilter filter) {
        if (filter.getGroupId() == null) {
            throw new ApplicationException(messageService.get("groupInvite.group.filter.not.informed.exception"));
        }

        return groupInviteRepository.findAll(GroupInviteSpecification.filter(filter), pageable);
    }

    @Transactional
    public GroupInvite insert(GroupInviteInsertRecord groupInviteInsertRecord) {
        // TODO: After security implementation, get user by token and verify if
        //  user is owner from group, just owners can create invites from group.

        Group group = groupService.findById(groupInviteInsertRecord.groupId());

        GroupInvite groupInvite = new GroupInvite();
        groupInvite.setGroup(group);
        groupInvite.setExpiresAt(groupInviteInsertRecord.expiresAt());
        groupInvite.setUses(0);
        return groupInviteRepository.save(groupInvite);
    }

    @Transactional
    public void accept(UUID hash, Long userId) {
        GroupInvite groupInvite = findByHash(hash);

        checkExpiration(groupInvite.getExpiresAt());

        User user = userService.findById(userId);

        checkIfTheUserHasAnyTypeOfLinkWithTheGroup(groupInvite.getGroup(), user);

        groupService.addUserToGroup(groupInvite.getGroup(), user);

        groupInvite.setUses(groupInvite.getUses() + 1);
        groupInviteRepository.save(groupInvite);
    }

    private void checkExpiration(LocalDateTime expiresAt) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(expiresAt)) {
            throw new ApplicationException(messageService.get("groupInvite.expired.exception"));
        }
    }

    private void checkIfTheUserHasAnyTypeOfLinkWithTheGroup(Group group, User user) {
        if (group.getOwner().getId().equals(user.getId())) {
            throw new ApplicationException(messageService.get("groupInvite..user.already.owner.exception"));
        }

        if (group.getUsers().contains(user)) {
            throw new ApplicationException(messageService.get("groupInvite..user.already.member.exception"));
        }
    }
}

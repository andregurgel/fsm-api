package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.commons.infrastructure.properties.GlobalProperties;
import dev.andregurgel.fsm_api.commons.infrastructure.util.PageController;
import dev.andregurgel.fsm_api.controller.dto.GroupInserRecord;
import dev.andregurgel.fsm_api.controller.dto.GroupInviteInsertRecord;
import dev.andregurgel.fsm_api.controller.filter.GroupInviteFilter;
import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.model.GroupInvite;
import dev.andregurgel.fsm_api.service.GroupInviteService;
import dev.andregurgel.fsm_api.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group_invite")
public class GroupInviteController implements PageController<GroupInvite, GroupInviteFilter> {

    private final GroupInviteService groupInviteService;
    private final GlobalProperties globalProperties;

    public GroupInviteController(GroupInviteService groupInviteService, GlobalProperties globalProperties) {
        this.groupInviteService = groupInviteService;
        this.globalProperties = globalProperties;
    }

    @GetMapping("/hash/{hash}")
    public ResponseEntity<GroupInvite> findByHash(@PathVariable UUID hash) {
        return ResponseEntity.ok(groupInviteService.findByHash(hash));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<GroupInvite>> findAllPage(Pageable pageable) {
        return ResponseEntity.ok(groupInviteService.findAllPageable(pageable));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<GroupInvite>> findAllPageFiltered(Pageable pageable, @ModelAttribute GroupInviteFilter filter) {
        return ResponseEntity.ok(groupInviteService.findAllPageableFiltered(pageable, filter));
    }

    @PostMapping
    public ResponseEntity<GroupInvite> insert(@RequestBody GroupInviteInsertRecord groupInviteInsertRecord) {
        var user = groupInviteService.insert(groupInviteInsertRecord);
        return ResponseEntity.created(URI.create("%s/group_invite/%s".formatted(globalProperties.getRoutes().getApiUrl(), user.getId()))).body(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/accept/{hash}/{userId}")
    public void accept(@PathVariable UUID hash, @PathVariable Long userId) {
        // TODO: Remove userId from path after implementing of security.
        groupInviteService.accept(hash, userId);
    }
}

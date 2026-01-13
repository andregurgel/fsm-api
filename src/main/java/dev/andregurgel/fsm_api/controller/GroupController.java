package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.commons.infrastructure.properties.GlobalProperties;
import dev.andregurgel.fsm_api.controller.dto.GroupInsertRecord;
import dev.andregurgel.fsm_api.controller.dto.GroupPatchRecord;
import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;
    private final GlobalProperties globalProperties;

    public GroupController(GroupService groupService,
                           GlobalProperties globalProperties) {
        this.groupService = groupService;
        this.globalProperties = globalProperties;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> findById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.findById(id));
    }

    // TODO: Remove 'userId' obligation after token jwt implementation, get owner information from token.
    @GetMapping("/find_all_from_user/{userId}")
    public ResponseEntity<List<Group>> findAllFromUser(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.findAllFromUser(userId));
    }

    @PostMapping
    public ResponseEntity<Group> insert(@RequestBody GroupInsertRecord groupInsertRecord) {
        var group = groupService.insert(groupInsertRecord);
        return ResponseEntity.created(URI.create("%s/group/%s".formatted(globalProperties.getRoutes().getApiUrl(), group.getId()))).body(group);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Group> patch(@PathVariable Long id, @RequestBody GroupPatchRecord groupPatchRecord) {
        return ResponseEntity.ok(groupService.patch(id, groupPatchRecord));
    }
}

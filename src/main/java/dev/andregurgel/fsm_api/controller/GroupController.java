package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.commons.infrastructure.properties.GlobalProperties;
import dev.andregurgel.fsm_api.controller.dto.GroupInserRecord;
import dev.andregurgel.fsm_api.controller.dto.UserInsertRecord;
import dev.andregurgel.fsm_api.controller.dto.UserPatchRecord;
import dev.andregurgel.fsm_api.controller.filter.UserFilter;
import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.service.GroupService;
import dev.andregurgel.fsm_api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // TODO: Remove 'userId' obligation after token jwt implementation, get owner information from token.
    @GetMapping("/find_all_from_user/{userId}")
    public ResponseEntity<List<Group>> findAllFromUser(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.findAllFromUser(userId));
    }

    @PostMapping
    public ResponseEntity<Group> insert(@RequestBody GroupInserRecord groupInserRecord) {
        var group = groupService.insert(groupInserRecord);
        return ResponseEntity.created(URI.create("%s/group/%s".formatted(globalProperties.getRoutes().getApiUrl(), group.getId()))).body(group);
    }
}

package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.commons.infrastructure.properties.GlobalProperties;
import dev.andregurgel.fsm_api.commons.infrastructure.util.PageController;
import dev.andregurgel.fsm_api.controller.dto.UserInsertRecord;
import dev.andregurgel.fsm_api.controller.dto.UserPatchRecord;
import dev.andregurgel.fsm_api.controller.filter.UserFilter;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController implements PageController<User, UserFilter> {

    private final UserService userService;
    private final GlobalProperties globalProperties;

    public UserController(UserService userService,
                          GlobalProperties globalProperties) {
        this.userService = userService;
        this.globalProperties = globalProperties;
    }

    @Override
    @RequestMapping
    public ResponseEntity<Page<User>> findAllPage(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllPageable(pageable));
    }

    @Override
    @RequestMapping("/search")
    public ResponseEntity<Page<User>> findAllPageFiltered(Pageable pageable, @ModelAttribute UserFilter filter) {
        return ResponseEntity.ok(userService.findAllPageableFiltered(pageable, filter));
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody UserInsertRecord userInsertRecord) {
        var user = userService.insert(userInsertRecord);
        return ResponseEntity.created(URI.create("%s/user/%s".formatted(globalProperties.getRoutes().getApiUrl(), user.getId()))).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patch(@PathVariable Long id, @RequestBody UserPatchRecord userPatchRecord) {
        return ResponseEntity.ok(userService.patch(id, userPatchRecord));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/activate/{id}")
    public void activate(@PathVariable Long id) {
        userService.activate(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/deactivate/{id}")
    public void deactivate(@PathVariable Long id) {
        userService.deactivate(id);
    }
}

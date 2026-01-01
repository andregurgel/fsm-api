package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.commons.infrastructure.properties.GlobalProperties;
import dev.andregurgel.fsm_api.controller.dto.UserInsertRecord;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final GlobalProperties globalProperties;

    public UserController(UserService userService,
                          GlobalProperties globalProperties) {
        this.userService = userService;
        this.globalProperties = globalProperties;
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody UserInsertRecord userInsertRecord) {
        var user = userService.insert(userInsertRecord);
        return ResponseEntity.created(URI.create("%s/user/%s".formatted(globalProperties.getRoutes().getApiUrl(), user.getId()))).body(user);
    }
}

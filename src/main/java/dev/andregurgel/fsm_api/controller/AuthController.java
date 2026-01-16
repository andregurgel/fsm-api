package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.commons.exception.ApplicationException;
import dev.andregurgel.fsm_api.controller.dto.AuthTokenResponse;
import dev.andregurgel.fsm_api.service.AuthService;
import dev.andregurgel.fsm_api.service.MessageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class AuthController {

    private final AuthService authService;

    private final MessageService messageService;

    public AuthController(AuthService authService,
                          MessageService messageService) {
        this.authService = authService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AuthTokenResponse> token(
            @RequestParam("grant_type") String grantType,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        if (!grantType.equals("password")) {
            throw new ApplicationException(messageService.get("grandType.error.exception"));
        }

        return ResponseEntity.ok(authService.login(username, password));
    }
}

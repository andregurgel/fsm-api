package dev.andregurgel.fsm_api.config;

import dev.andregurgel.fsm_api.commons.exception.ApplicationException;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.repository.UserRepository;
import dev.andregurgel.fsm_api.service.MessageService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final MessageService messageService;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  MessageService messageService) {
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ApplicationException(messageService.get("user.not.found.general.exception")));

        return new CustomUserDetails(user);
    }
}


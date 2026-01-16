package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.commons.exception.ApplicationException;
import dev.andregurgel.fsm_api.controller.dto.UserInsertRecord;
import dev.andregurgel.fsm_api.controller.dto.UserPatchRecord;
import dev.andregurgel.fsm_api.controller.filter.UserFilter;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.repository.UserRepository;
import dev.andregurgel.fsm_api.repository.spec.UserSpecification;
import dev.andregurgel.fsm_api.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageService messageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       MessageService messageService,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.messageService = messageService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException(messageService.get("user.not.found.exception", id));
        }

        return userOpt.get();
    }

    public Page<User> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findAllPageableFiltered(Pageable pageable, UserFilter filter) {
        return userRepository.findAll(UserSpecification.filter(filter), pageable);
    }

    public User insert(UserInsertRecord userInsertRecord) {
        User user = new User();
        user.setName(userInsertRecord.name());
        user.setEmail(userInsertRecord.email());
        user.setPassword(bCryptPasswordEncoder.encode(userInsertRecord.password()));
        user.setPhone(userInsertRecord.phone());
        user.setActive(true);
        return userRepository.save(user);
    }

    public User patch(Long id, UserPatchRecord userPatchRecord) {
        User user = findById(id);
        userMapper.patch(userPatchRecord, user);
        return userRepository.save(user);
    }

    public void activate(Long id) {
        User user = findById(id);
        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void deactivate(Long id) {
        User user = findById(id);
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}

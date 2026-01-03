package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.controller.dto.UserInsertRecord;
import dev.andregurgel.fsm_api.controller.dto.UserPatchRecord;
import dev.andregurgel.fsm_api.controller.filter.UserFilter;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.repository.UserRepository;
import dev.andregurgel.fsm_api.repository.spec.UserSpecification;
import dev.andregurgel.fsm_api.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findAllPageableFiltered(Pageable pageable, UserFilter filter) {
        return userRepository.findAll(UserSpecification.filter(filter), pageable);
    }

    public User insert(UserInsertRecord userInsertRecord) {
        try {
            User user = new User();
            user.setName(userInsertRecord.name());
            user.setEmail(userInsertRecord.email());
            user.setPassword(userInsertRecord.password());
            user.setPhone(userInsertRecord.phone());
            user.setActive(true);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public User patch(Long id, UserPatchRecord userPatchRecord) {
        try {
            User user = findById(id);
            userMapper.patch(userPatchRecord, user);
            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
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

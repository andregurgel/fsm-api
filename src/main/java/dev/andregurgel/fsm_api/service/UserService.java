package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.controller.dto.UserInsertRecord;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public User insert(UserInsertRecord userInsertRecord) {
        try {
            User user = new User();
            user.setName(userInsertRecord.name());
            user.setEmail(userInsertRecord.email());
            user.setPassword(bCryptPasswordEncoder.encode(userInsertRecord.password()));
            user.setPhone(userInsertRecord.phone());
            user.setActive(true);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

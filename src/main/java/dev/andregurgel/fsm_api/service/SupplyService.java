package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.commons.exception.ApplicationException;
import dev.andregurgel.fsm_api.controller.dto.SupplyInsertRecord;
import dev.andregurgel.fsm_api.controller.filter.SupplyFilter;
import dev.andregurgel.fsm_api.model.Supply;
import dev.andregurgel.fsm_api.model.User;
import dev.andregurgel.fsm_api.model.Vehicle;
import dev.andregurgel.fsm_api.repository.SupplyRepository;
import dev.andregurgel.fsm_api.repository.spec.SupplySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final MessageService messageService;

    public SupplyService(SupplyRepository supplyRepository,
                         VehicleService vehicleService,
                         UserService userService,
                         MessageService messageService) {
        this.supplyRepository = supplyRepository;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.messageService = messageService;
    }

    public Supply findById(UUID id) {
        return supplyRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(messageService.get("supply.not.found.exception", id)));
    }

    public Page<Supply> findAllPageableFiltered(Pageable pageable, SupplyFilter filter) {
        if (filter.getGroupId() == null) {
            throw new ApplicationException(messageService.get("supply.group.filter.not.informed.exception"));
        }

        return supplyRepository.findAll(SupplySpecification.filter(filter), pageable);
    }

    @Transactional
    public Supply insert(SupplyInsertRecord record) {
        Vehicle vehicle = vehicleService.findById(record.vehicleId());
        
        User user;
        if (record.userId() != null) {
            user = userService.findById(record.userId());
        } else {
            user = getCurrentUser();
        }

        Supply supply = new Supply();
        supply.setVehicle(vehicle);
        supply.setUser(user);
        supply.setLiter(record.liter());
        supply.setPrice(record.price());
        supply.setCurrentMileage(record.currentMileage());
        supply.setType(record.type());
        supply.setSuppliedAt(record.suppliedAt());

        return supplyRepository.save(supply);
    }

    @Transactional
    public void delete(UUID id) {
        Supply supply = findById(id);
        supply.setDeleted(true);
        supply.setDeletedAt(LocalDateTime.now());
        supplyRepository.save(supply);
    }

    private User getCurrentUser() {
        try {
            String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
            Long userId = Long.parseLong(userIdStr);
            return userService.findById(userId);
        } catch (Exception e) {
            throw new ApplicationException(messageService.get("user.not.authenticated.exception"));
        }
    }
}

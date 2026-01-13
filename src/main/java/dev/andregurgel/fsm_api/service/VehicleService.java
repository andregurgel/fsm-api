package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.commons.exception.ApplicationException;
import dev.andregurgel.fsm_api.controller.dto.VehicleInsertRecord;
import dev.andregurgel.fsm_api.controller.dto.VehiclePatchRecord;
import dev.andregurgel.fsm_api.controller.filter.VehicleFilter;
import dev.andregurgel.fsm_api.model.Group;
import dev.andregurgel.fsm_api.model.Vehicle;
import dev.andregurgel.fsm_api.repository.VehicleRepository;
import dev.andregurgel.fsm_api.repository.spec.VehicleSpecification;
import dev.andregurgel.fsm_api.service.mapper.VehicleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final GroupService groupService;

    private final VehicleMapper vehicleMapper;

    private final MessageService messageService;

    public VehicleService(VehicleRepository vehicleRepository,
                          GroupService groupService,
                          VehicleMapper vehicleMapper,
                          MessageService messageService) {
        this.vehicleRepository = vehicleRepository;
        this.groupService = groupService;
        this.vehicleMapper = vehicleMapper;
        this.messageService = messageService;
    }

    public Vehicle findById(Long id) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
        if (vehicleOpt.isEmpty()) {
            throw new ApplicationException(messageService.get("vehicle.not.found.exception"));
        }

        return vehicleOpt.get();
    }

    public Page<Vehicle> findAllPage(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    public Page<Vehicle> findAllPageFiltered(Pageable pageable, VehicleFilter filter) {
        if (filter.getGroupId() == null) {
            throw new ApplicationException(messageService.get("vehicle.group.filter.not.informed.exception"));
        }

        return vehicleRepository.findAll(VehicleSpecification.filter(filter), pageable);
    }

    @Transactional
    public Vehicle insert(VehicleInsertRecord vehicleInsertRecord) {
        // TODO: After security implementation, get user by token and verify if
        //  user is owner from group, just owners can create vehicles on group.

        Group group = groupService.findById(vehicleInsertRecord.groupId());

        Vehicle vehicle = new Vehicle();
        vehicle.setDescription(vehicleInsertRecord.description());
        vehicle.setType(vehicleInsertRecord.type());
        vehicle.setPlate(vehicleInsertRecord.plate());
        vehicle.setGroup(group);
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle patch(Long id, VehiclePatchRecord vehiclePatchRecord) {
        Vehicle vehicle = findById(id);
        vehicleMapper.patch(vehiclePatchRecord, vehicle);
        return vehicleRepository.save(vehicle);
    }
}

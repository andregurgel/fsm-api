package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.commons.infrastructure.properties.GlobalProperties;
import dev.andregurgel.fsm_api.commons.infrastructure.util.PageController;
import dev.andregurgel.fsm_api.controller.dto.VehicleInsertRecord;
import dev.andregurgel.fsm_api.controller.dto.VehiclePatchRecord;
import dev.andregurgel.fsm_api.controller.filter.VehicleFilter;
import dev.andregurgel.fsm_api.model.Vehicle;
import dev.andregurgel.fsm_api.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/vehicle")
public class VehicleController implements PageController<Vehicle, VehicleFilter> {

    private final VehicleService vehicleService;

    private final GlobalProperties globalProperties;

    public VehicleController(VehicleService vehicleService,
                             GlobalProperties globalProperties) {
        this.vehicleService = vehicleService;
        this.globalProperties = globalProperties;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<Vehicle>> findAllPage(Pageable pageable) {
        return ResponseEntity.ok(vehicleService.findAllPage(pageable));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<Vehicle>> findAllPageFiltered(Pageable pageable, @ModelAttribute VehicleFilter filter) {
        return ResponseEntity.ok(vehicleService.findAllPageFiltered(pageable, filter));
    }

    @PostMapping
    public ResponseEntity<Vehicle> insert(@RequestBody VehicleInsertRecord vehicleInsertRecord) {
        Vehicle vehicle = vehicleService.insert(vehicleInsertRecord);
        return ResponseEntity.created(URI.create("%s/vehicle/%s".formatted(globalProperties.getRoutes().getApiUrl(), vehicle.getId()))).body(vehicle);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> patch(@PathVariable Long id, @RequestBody VehiclePatchRecord userPatchRecord) {
        return ResponseEntity.ok(vehicleService.patch(id, userPatchRecord));
    }
}

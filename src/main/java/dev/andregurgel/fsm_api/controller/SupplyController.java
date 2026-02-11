package dev.andregurgel.fsm_api.controller;

import dev.andregurgel.fsm_api.controller.dto.SupplyInsertRecord;
import dev.andregurgel.fsm_api.controller.filter.SupplyFilter;
import dev.andregurgel.fsm_api.model.Supply;
import dev.andregurgel.fsm_api.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/supply")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(supplyService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Supply>> findAll(Pageable pageable, SupplyFilter filter) {
        return ResponseEntity.ok(supplyService.findAllPageableFiltered(pageable, filter));
    }

    @PostMapping
    public ResponseEntity<Supply> insert(@RequestBody @Valid SupplyInsertRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplyService.insert(record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        supplyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

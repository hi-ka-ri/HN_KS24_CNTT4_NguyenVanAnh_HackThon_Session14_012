package javaservice.mockhackthon.controller;

import javaservice.mockhackthon.model.dto.request.CarDTO;
import javaservice.mockhackthon.model.dto.response.CarResponse;
import javaservice.mockhackthon.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<Page<CarResponse>> getAll(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            Pageable pageable
    ) {
        return ResponseEntity.ok(carService.getAll(model, brand, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CarResponse> create(@RequestBody CarDTO carDTO) {
        CarResponse response = carService.create(carDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> update(
            @PathVariable Long id,
            @RequestBody CarDTO carDTO
    ) {
        return ResponseEntity.ok(carService.update(id, carDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarResponse> patch(
            @PathVariable Long id,
            @RequestBody CarDTO carDTO
    ) {
        return ResponseEntity.ok(carService.patch(id, carDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
        carService.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
}
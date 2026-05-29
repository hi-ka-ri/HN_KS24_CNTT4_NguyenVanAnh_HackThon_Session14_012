package javaservice.mockhackthon.service;

import javaservice.mockhackthon.model.dto.request.CarDTO;
import javaservice.mockhackthon.model.dto.response.CarResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    Page<CarResponse> getAll(String model, String brand, Pageable pageable);
    CarResponse getById(Long id);
    CarResponse create(CarDTO carDTO);
    CarResponse update(Long id, CarDTO carDTO);
    CarResponse patch(Long id, CarDTO carDTO);
    void delete(Long id);
    void hardDelete(Long id);
}

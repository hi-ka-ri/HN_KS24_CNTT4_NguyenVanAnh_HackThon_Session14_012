package javaservice.mockhackthon.service.impl;

import javaservice.mockhackthon.exeption.DuplicateResourceException;
import javaservice.mockhackthon.exeption.ResourceNotFoundException;
import javaservice.mockhackthon.model.dto.request.CarDTO;
import javaservice.mockhackthon.model.dto.response.CarResponse;
import javaservice.mockhackthon.model.entities.Car;
import javaservice.mockhackthon.model.entities.StatusCar;
import javaservice.mockhackthon.repository.CarRepository;
import javaservice.mockhackthon.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private CarResponse toResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .model(car.getModel())
                .brand(car.getBrand())
                .price(car.getPrice())
                .statusCar(car.getStatusCar())
                .build();
    }

    @Override
    public Page<CarResponse> getAll(String model, String brand, Pageable pageable) {
        log.info("Lấy danh sách xe - model: {}, brand: {}, page: {}, size: {}",
                model, brand, pageable.getPageNumber(), pageable.getPageSize());

        return carRepository
                .searchByModelOrBrand(model, brand, pageable)
                .map(this::toResponse);
    }

    @Override
    public CarResponse getById(Long id) {
        log.info("Lấy thông tin xe id: {}", id);

        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xe với id: " + id));

        return toResponse(car);
    }

    @Override
    public CarResponse create(CarDTO carDTO) {
        log.info("Thêm xe - model: {}, brand: {}, price: {}",
                carDTO.getModel(), carDTO.getBrand(), carDTO.getPrice());

        if (carRepository.existsByModelAndDeletedFalse(carDTO.getModel())) {
            throw new DuplicateResourceException("Xe với model '" + carDTO.getModel() + "' đã tồn tại");
        }

        Car car = Car.builder()
                .model(carDTO.getModel())
                .brand(carDTO.getBrand())
                .price(carDTO.getPrice())
                .statusCar(carDTO.getStatusCar() != null ? carDTO.getStatusCar() : StatusCar.AVAILABLE)
                .deleted(false)
                .build();

        Car saved = carRepository.save(car);

        log.info("Thêm xe thành công, id: {}", saved.getId());

        return toResponse(saved);
    }

    @Override
    public CarResponse update(Long id, CarDTO carDTO) {
        log.info("Cập nhật toàn bộ xe id: {}", id);

        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xe với id: " + id));

        if (!car.getModel().equals(carDTO.getModel())
                && carRepository.existsByModelAndDeletedFalseAndIdNot(carDTO.getModel(), id)) {
            throw new DuplicateResourceException("Xe với model '" + carDTO.getModel() + "' đã tồn tại");
        }

        car.setModel(carDTO.getModel());
        car.setBrand(carDTO.getBrand());
        car.setPrice(carDTO.getPrice());
        car.setStatusCar(carDTO.getStatusCar() != null ? carDTO.getStatusCar() : StatusCar.AVAILABLE);

        Car updated = carRepository.save(car);

        log.info("Cập nhật xe thành công, id: {}", updated.getId());

        return toResponse(updated);
    }

    @Override
    public CarResponse patch(Long id, CarDTO carDTO) {
        log.info("Cập nhật một phần xe id: {}", id);

        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xe với id: " + id));

        if (carDTO.getModel() != null && !carDTO.getModel().equals(car.getModel())) {
            if (carRepository.existsByModelAndDeletedFalseAndIdNot(carDTO.getModel(), id)) {
                throw new DuplicateResourceException("Xe với model '" + carDTO.getModel() + "' đã tồn tại");
            }
            car.setModel(carDTO.getModel());
        }

        if (carDTO.getBrand() != null) {
            car.setBrand(carDTO.getBrand());
        }

        if (carDTO.getPrice() != null) {
            car.setPrice(carDTO.getPrice());
        }

        if (carDTO.getStatusCar() != null) {
            car.setStatusCar(carDTO.getStatusCar());
        }

        Car updated = carRepository.save(car);

        log.info("Patch xe thành công, id: {}", updated.getId());

        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Xóa mềm xe id: {}", id);

        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xe với id: " + id));

        car.setDeleted(true);
        carRepository.save(car);

        log.info("Xóa mềm xe thành công, id: {}", id);
    }

    @Override
    public void hardDelete(Long id) {
        log.info("Xóa cứng xe id: {}", id);

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xe với id: " + id));

        carRepository.delete(car);

        log.info("Xóa cứng xe thành công, id: {}", id);
    }
}
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
import org.antlr.v4.runtime.CodePointBuffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.apache.tomcat.util.net.openssl.OpenSSLStatus.getName;

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
        log.info("Lấy thông tin thuốc id: {}", id);
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xe với id: " + id));
        return toResponse(car);
    }

    @Override
    public CarResponse create(CarDTO carDTO) {
        log.info("Create Car model: {} brand: {}, price: {} ",carDTO.getModel(), carDTO.getBrand(), carDTO.getPrice());
        if (carRepository.existsByModel(carDTO.getModel())) {
            throw new DuplicateResourceException("Xe '" + carDTO + "' đã tồn tại");
        }
        Car car = Car.builder()
                .model(carDTO.getModel())
                        .brand(carDTO.getBrand())
                                .price(carDTO.getPrice())
                                        .statusCar(carDTO.getStatusCar() != null
                                        ? carDTO.getStatusCar()
                                                : StatusCar.AVAILABLE)
                                                .deleted(false)
                .build();
        Car saved = carRepository.save(car);
        log.info("them xe thanh cong, id: {}", saved.getId());
        return toResponse(saved);
    }

    private CodePointBuffer.Builder deleted(boolean b) {

    }

    @Override
    public CarResponse update(Long id, CarDTO carDTO) {
        log.info("Cập nhật toàn bộ thuốc id: {}", id);
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thuốc với id: " + id));
        if (!car.getModel().equals(carDTO.getModel())
                && carRepository.existsByModel(carDTO.getModel())) {
            throw new DuplicateResourceException("Thuốc với tên '" + carDTO.getModel() + "' đã tồn tại");
        }
        car.setModel(carDTO.getModel());
        car.setBrand(carDTO.getBrand());
        car.setPrice(carDTO.getPrice());
        car.setStatusCar(carDTO.getStatusCar());
        car updated = carRepository.save(car);
        log.info("Cập nhật thuốc thành công, id: {}", updated.get());
        return toResponse(updated);
    }

    @Override
    public CarResponse patch(Long id, CarDTO carDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void hardDelete(Long id) {

    }
}

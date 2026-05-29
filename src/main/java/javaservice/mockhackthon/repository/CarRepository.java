package javaservice.mockhackthon.repository;

import javaservice.mockhackthon.model.entities.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findAllByDeletedFalse(Pageable pageable);

    Optional<Car> findByIdAndDeletedFalse(Long id);

    boolean existsByModel(String model);

    // Tìm kiếm theo model hoặc brand, kết hợp phân trang, bỏ qua bản đã xóa
    @Query("SELECT m FROM Car m WHERE m.is_delete = false " +
            "AND (:name IS NULL OR LOWER(m.model) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:manufacturer IS NULL OR LOWER(m.brand) LIKE LOWER(CONCAT('%', :manufacturer, '%')))")
    Page<Car> searchByModelOrBrand(
            @Param("Model") String model,
            @Param("Brand") String brand,
            Pageable pageable);
}

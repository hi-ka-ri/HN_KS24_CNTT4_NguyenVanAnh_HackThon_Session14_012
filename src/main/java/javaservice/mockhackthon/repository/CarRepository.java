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

    boolean existsByModelAndDeletedFalse(String model);

    boolean existsByModelAndDeletedFalseAndIdNot(String model, Long id);

    @Query("""
            SELECT c FROM Car c
            WHERE c.deleted = false
            AND (:model IS NULL OR LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%')))
            AND (:brand IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
            """)
    Page<Car> searchByModelOrBrand(
            @Param("model") String model,
            @Param("brand") String brand,
            Pageable pageable
    );
}
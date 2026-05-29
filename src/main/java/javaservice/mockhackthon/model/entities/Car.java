package javaservice.mockhackthon.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
@Builder
public class Car {

    @Id
    @Column(name = "car_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_car", nullable = false)
    private StatusCar statusCar;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
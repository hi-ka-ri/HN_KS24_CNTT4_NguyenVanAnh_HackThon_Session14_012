package javaservice.mockhackthon.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
@Builder
public class Car {
    @Id
    @Column(name = "car_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "model",nullable = false)
    private String model;
    @Column(name = "brand",unique = true,nullable = false)
    private String brand;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name = "stock",nullable = false)
    private StatusCar statusCar;
    @Column(name = "status",nullable = false)
    private boolean deleted;
}

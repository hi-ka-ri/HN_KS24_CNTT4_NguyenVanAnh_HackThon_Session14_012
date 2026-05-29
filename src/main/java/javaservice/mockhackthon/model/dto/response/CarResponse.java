package javaservice.mockhackthon.model.dto.response;

import aQute.bnd.annotation.headers.BundleCategory;
import javaservice.mockhackthon.model.entities.StatusCar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponse {
    private Long id;
    private String model;
    private String brand;
    private Double price;
    private StatusCar statusCar;
}

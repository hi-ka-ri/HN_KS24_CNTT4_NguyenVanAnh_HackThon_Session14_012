package javaservice.mockhackthon.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javaservice.mockhackthon.model.entities.StatusCar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTO {
    @NotBlank(message = "You can not be empty")
    private String model;
    @NotBlank(message = "You can not be empty")
    private String brand;
    @NotNull(message = "You can not be empty")
    @Min(value = 1, message = "You must not be more than 0")
    private Double price;
    private StatusCar statusCar;
    private boolean deleted;
}

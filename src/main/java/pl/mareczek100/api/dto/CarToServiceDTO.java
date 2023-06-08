package pl.mareczek100.api.dto;

import lombok.*;

import java.util.Set;


@With
@Value
@Builder
@EqualsAndHashCode(of = "vin")
@ToString(exclude = "carServiceRequests")
public class CarToServiceDTO {

    String vin;
    String brand;
    String model;
    Short year;
    Set<CarServiceRequestDTO> carServiceRequests;

}
package pl.mareczek100.domain;

import lombok.*;
import pl.mareczek100.domain.inputTrafficData.CarServiceRequestInputData;

import java.util.Set;


@With
@Value
@Builder
@EqualsAndHashCode(of = "vin")
@ToString(exclude = "carServiceRequests")
public class CarToService {

    Integer carToServiceId;
    String vin;
    String brand;
    String model;
    Short year;
    Set<CarServiceRequestInputData> carServiceRequests;

}
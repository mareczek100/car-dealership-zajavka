package pl.mareczek100.api.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;


@Builder
public class CarServiceRequestDTO {


    String carServiceRequestNumber;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    String comment;
    CustomerDTO customer;
    CarDTO carToService;


}

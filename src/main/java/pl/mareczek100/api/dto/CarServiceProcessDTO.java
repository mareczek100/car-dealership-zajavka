package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;


@Builder
@Value
public class CarServiceProcessDTO {


    String carServiceRequestNumber;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    String customerComment;
    CustomerDTO customer;
    CarServiceDTO carToService;
    Short workHours;
    List<String> mechanicComment;
    List<MechanicDTO> mechanic;
    List<ServiceDTO> service;
    List<Short> partQuantity;
    List<PartDTO> parts;

}

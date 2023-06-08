package pl.mareczek100.api.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@ToString(exclude = {"carServiceHandling","carServicePart", "customer", "carToService"})
@EqualsAndHashCode(of = "carServiceRequestNumber")
public class CarServiceRequestDTO {
    Integer carServiceRequestId;
    String carServiceRequestNumber;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    String comment;
    CustomerDTO customer;
    CarToServiceDTO carToService;
    Set<CarServiceHandlingDTO> carServiceHandling;
    Set<CarServicePartsDTO> carServicePart;

}

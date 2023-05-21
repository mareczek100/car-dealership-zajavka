package pl.mareczek100.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@ToString(exclude = {"carServiceHandlingEntities","carServicePartEntities"})
@EqualsAndHashCode(of = "carServiceRequestNumber")
public class CarServiceRequest {
    Integer carServiceRequestId;
    String carServiceRequestNumber;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    String customerComment;
    Customer customer;
    CarToService carToService;
    Set<CarServiceHandling> carServiceHandlingEntities;
    Set<CarServiceParts> carServicePartEntities;

}

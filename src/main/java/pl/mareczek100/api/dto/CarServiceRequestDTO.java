package pl.mareczek100.api.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;


@Builder
@With
public record CarServiceRequestDTO   (String carServiceRequestNumber,
                                      OffsetDateTime receivedDateTime,
                                      OffsetDateTime completedDateTime,
                                      String comment,
                                      CustomerDTO customer,
                                      CarDTO carToService){

}

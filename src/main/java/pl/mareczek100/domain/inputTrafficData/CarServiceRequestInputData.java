package pl.mareczek100.domain.inputTrafficData;

import lombok.*;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.domain.Customer;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
public class CarServiceRequestInputData {

    Customer customer;
    CarToService carToService;
    String customerComment;

}

package pl.mareczek100.domain.inputTrafficData;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.domain.Customer;

@With
@Value
@Builder
public class CarServiceRequestInputData {

    Customer customer;
    CarToService carToService;
    String customerComment;

}
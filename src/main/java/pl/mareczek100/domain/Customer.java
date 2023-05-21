package pl.mareczek100.domain;

import lombok.*;
import pl.mareczek100.domain.inputTrafficData.CarServiceRequestInputData;

import java.util.Set;

@With
@Value
@Builder
@ToString(exclude = {"address", "carServiceRequests", "invoices"})
@EqualsAndHashCode(of = "email")
public class Customer {

    Integer customerId;
    String name;
    String surname;
    String phone;
    String email;
    Address address;
    Set<CarServiceRequestInputData> carServiceRequests;
    Set<Invoice> invoices;


}

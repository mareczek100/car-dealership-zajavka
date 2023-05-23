package pl.mareczek100.domain;

import lombok.*;

import java.util.Set;
@With
@Value
@Builder
@ToString(exclude = "customers")
@EqualsAndHashCode(of = {"country", "city", "postalCode", "street", "buildingFlatNumber"})
public class Address {

    Integer addressId;
    String country;
    String city;
    String postalCode;
    String street;
    String buildingFlatNumber;
    Set<Customer> customers;
}

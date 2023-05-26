package pl.mareczek100.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@ToString(exclude = {"carServiceRequests", "invoices"})
@EqualsAndHashCode(of = "email")
public class Customer {

    Integer customerId;
    String name;
    String surname;
    String phone;
    String email;
    Address address;
    Set<CarServiceRequest> carServiceRequests;
    Set<Invoice> invoices;

    public Set<CarServiceRequest> getCarServiceRequests() {
        return Objects.isNull(carServiceRequests) ? new HashSet<>() : carServiceRequests;
    }
    public Set<Invoice> getInvoices() {
        return Objects.isNull(invoices) ? new HashSet<>() : invoices;
    }
}

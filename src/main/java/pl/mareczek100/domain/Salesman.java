package pl.mareczek100.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "pesel")
@ToString(exclude = "invoices")
public class Salesman {

    Integer salesmanId;
    String name;
    String surname;
    String pesel;
    Set<Invoice> invoices;
}
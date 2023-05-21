package pl.mareczek100.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = "invoiceNumber")
public class Invoice {
    Integer invoiceId;
    String invoiceNumber;
    OffsetDateTime dateTime;
    CarToSell carToSell;
    Customer customer;
    Salesman salesman;

}

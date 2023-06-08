package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.OffsetDateTime;
@Builder
public class InvoiceDTO {
    String invoiceNumber; OffsetDateTime dateTime; CarDTO car; SalesmanDTO salesman;
}
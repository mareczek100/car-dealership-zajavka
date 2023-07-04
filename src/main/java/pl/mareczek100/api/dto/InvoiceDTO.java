package pl.mareczek100.api.dto;

import lombok.Builder;

import java.time.OffsetDateTime;
@Builder
public record InvoiceDTO (String invoiceNumber,
                          OffsetDateTime dateTime,
                          CarDTO carToSell,
                          SalesmanDTO salesman,
                          CustomerDTO customer){
}
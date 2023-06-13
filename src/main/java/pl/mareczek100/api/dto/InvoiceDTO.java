package pl.mareczek100.api.dto;

import java.time.OffsetDateTime;

public record InvoiceDTO (String invoiceNumber,
                          OffsetDateTime dateTime,
                          CarDTO carToSell,
                          SalesmanDTO salesman,
                          CustomerDTO customer){
}
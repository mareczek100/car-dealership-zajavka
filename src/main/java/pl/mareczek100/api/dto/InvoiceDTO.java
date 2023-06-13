package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.OffsetDateTime;

public record InvoiceDTO (String invoiceNumber,
                          OffsetDateTime dateTime,
                          CarDTO carToSell,
                          SalesmanDTO salesman,
                          CustomerDTO customer){
}
package pl.mareczek100.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "vin")
@ToString(exclude = "invoice")
public class CarToSell {

    Integer carToSellId;
    String vin;
    String brand;
    String model;
    Short year;
    String color;
    BigDecimal price;
    Invoice invoice;

}
package pl.mareczek100.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "vin")
public class CarToSellTempStorage {

    Integer carToSellTempStorageId;
    String vin;
    String brand;
    String model;
    Short year;
    String color;
    BigDecimal price;


    @Override
    public String toString() {
        return "CarToSell: " +
                "Id=" + carToSellTempStorageId +
                ", vin=" + vin +
                ", brand=" + brand +
                ", model=" + model +
                ", year=" + year +
                ", color=" + color +
                ", price=" + price;
    }
}
package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(of = "vin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_to_sell_temp_storage")
public class CarToSellTempStorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_to_sell_temp_storage_id")
    private Integer carToSellTempStorageId;
    @Column(name = "vin", unique = true)
    private String vin;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "year")
    private Short year;
    @Column(name = "color")
    private String color;
    @Column(name = "price")
    private BigDecimal price;


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
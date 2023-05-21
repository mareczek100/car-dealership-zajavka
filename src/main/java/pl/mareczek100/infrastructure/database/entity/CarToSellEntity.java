package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(of = "vin")
@ToString(exclude = "invoice")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_to_sell")
public class CarToSellEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_to_sell_id")
    private Integer carToSellId;
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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "carToSell")
    private InvoiceEntity invoiceEntity;

}
package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Entity
@EqualsAndHashCode(of = "vin")
@ToString(exclude = "carServiceRequests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_to_service")
public class CarToServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_to_service_id")
    private Integer carToServiceId;

    @Column(name = "vin", unique = true)
    private String vin;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Short year;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carToService")
    private Set<CarServiceRequestEntity> carServiceRequests;

}
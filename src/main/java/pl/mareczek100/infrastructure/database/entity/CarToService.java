package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;


@Data
@Entity
@EqualsAndHashCode(of = "vin")
@ToString(exclude = "carServiceRequests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_to_service")
public class CarToService {
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
    @Fetch(FetchMode.JOIN)
    private Set<CarServiceRequest> carServiceRequests;

}
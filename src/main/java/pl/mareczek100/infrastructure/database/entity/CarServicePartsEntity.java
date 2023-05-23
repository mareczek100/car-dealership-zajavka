package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@EqualsAndHashCode(of = "carServicePartsId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_service_parts")
public class CarServicePartsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_service_parts_id")
    private Integer carServicePartsId;
    @Column(name = "quantity")
    private Short quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_service_request_id")
    private CarServiceRequestEntity carServiceRequest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private PartEntity part;
}

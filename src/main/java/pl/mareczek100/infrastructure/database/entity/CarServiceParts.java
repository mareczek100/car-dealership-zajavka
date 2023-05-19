package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@EqualsAndHashCode(of = "carServicePartsId")
@ToString(exclude = "part")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_service_parts")
public class CarServiceParts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_service_parts_id")
    private Integer carServicePartsId;
    @Column(name = "quantity")
    private Short quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_service_request_id")
    private CarServiceRequest carServiceRequest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;
}

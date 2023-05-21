package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Entity
@EqualsAndHashCode(of = "carServiceHandlingId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_service_handling")
public class CarServiceHandlingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_service_handling_id")
    private Integer carServiceHandlingId;
    @Column(name = "hours")
    private Short hours;
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "car_service_request_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private CarServiceRequestEntity carServiceRequestEntity;
    @JoinColumn(name = "mechanic_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private MechanicEntity mechanicEntity;
    @JoinColumn(name = "service_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private ServiceEntity serviceEntity;

}

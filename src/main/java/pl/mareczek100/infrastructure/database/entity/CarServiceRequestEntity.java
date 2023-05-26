package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Entity
@ToString(exclude = {"carServicePart", "customer", "carToService"})
@EqualsAndHashCode(of = "carServiceRequestNumber")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_service_request")
public class CarServiceRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_service_request_id")
    private Integer carServiceRequestId;
    @Column(name = "car_service_request_number", unique = true)
    private String carServiceRequestNumber;
    @Column(name = "received_date_time")
    private OffsetDateTime receivedDateTime;
    @Column(name = "completed_date_time")
    private OffsetDateTime completedDateTime;
    @Column(name = "customer_comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "car_to_service_id")
    private CarToServiceEntity carToService;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carServiceRequest")
    @Fetch(FetchMode.JOIN)
    private Set<CarServiceHandlingEntity> carServiceHandling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carServiceRequest")
    @Fetch(FetchMode.JOIN)
    private Set<CarServicePartsEntity> carServicePart;


}
